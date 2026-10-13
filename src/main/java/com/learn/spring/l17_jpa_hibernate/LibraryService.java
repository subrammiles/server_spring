package com.learn.spring.l17_jpa_hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.spring.l17_jpa_hibernate.entity.Address;
import com.learn.spring.l17_jpa_hibernate.entity.Author;
import com.learn.spring.l17_jpa_hibernate.entity.Book;
import com.learn.spring.l17_jpa_hibernate.entity.Genre;
import com.learn.spring.l17_jpa_hibernate.entity.Tag;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class LibraryService {

    /** A thread-safe proxy: each transaction gets its own real EntityManager (persistence context). */
    @PersistenceContext
    private EntityManager em;

    private final Statistics stats;

    public LibraryService(EntityManagerFactory emf) {
        this.stats = emf.unwrap(SessionFactory.class).getStatistics();
    }

    // ---------- create ----------

    public Long seedSampleData() {
        Tag classic = new Tag("classic");
        Tag bestseller = new Tag("bestseller");
        em.persist(classic);
        em.persist(bestseller);

        Author tolkien = new Author("Tolkien", new Address("Oxford", "UK"));
        tolkien.addBook(new Book("The Hobbit", 1937, Genre.FICTION)).getTags().add(classic);
        tolkien.addBook(new Book("The Lord of the Rings", 1954, Genre.FICTION)).getTags().addAll(List.of(classic, bestseller));

        Author bloch = new Author("Joshua Bloch", new Address("San Jose", "USA"));
        bloch.addBook(new Book("Effective Java", 2001, Genre.TECH)).getTags().add(bestseller);
        bloch.addBook(new Book("Java Puzzlers", 2005, Genre.TECH));

        Author harari = new Author("Yuval Harari", new Address("Jerusalem", "Israel"));
        harari.addBook(new Book("Sapiens", 2011, Genre.HISTORY)).getTags().add(bestseller);

        // persist() makes the object MANAGED; cascade = ALL persists the books too
        em.persist(tolkien);
        em.persist(bloch);
        em.persist(harari);
        return tolkien.getId();
    }

    // ---------- read ----------

    @Transactional(readOnly = true)
    public Author findAuthor(Long id) {
        return em.find(Author.class, id);   // returned DETACHED: the transaction ends with this method
    }

    /** JPQL queries ENTITIES and their fields, not tables and columns. */
    @Transactional(readOnly = true)
    public List<Book> findBooksByAuthorName(String authorName) {
        return em.createQuery("""
                        SELECT b FROM Book b
                        WHERE b.author.name = :name
                        ORDER BY b.publishedYear""", Book.class)
                .setParameter("name", authorName)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public List<AuthorBookCount> bookCountPerAuthor() {
        return em.createQuery("""
                        SELECT new com.learn.spring.l17_jpa_hibernate.AuthorBookCount(a.name, COUNT(b))
                        FROM Author a LEFT JOIN a.books b
                        GROUP BY a.name
                        ORDER BY a.name""", AuthorBookCount.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public List<String> titlesTagged(String tag) {
        return em.createQuery("SELECT b.title FROM Book b JOIN b.tags t WHERE t.name = :tag ORDER BY b.title", String.class)
                .setParameter("tag", tag)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public long countBooksWithNativeSql() {
        return ((Number) em.createNativeQuery("SELECT COUNT(*) FROM book").getSingleResult()).longValue();
    }

    // ---------- update ----------

    /**
     * DIRTY CHECKING: no save()/update() call. The author is managed, so Hibernate notices
     * the change and issues the UPDATE automatically when the transaction commits.
     */
    public void renameAuthor(Long id, String newName) {
        Author author = em.find(Author.class, id);
        author.setName(newName);
    }

    /** merge() copies a DETACHED object's state onto a managed one. */
    public Author updateDetached(Author detached) {
        return em.merge(detached);
    }

    // ---------- delete ----------

    /** orphanRemoval: removing the book from the author's list deletes the row. */
    public void removeFirstBook(Long authorId) {
        Author author = em.find(Author.class, authorId);
        author.removeBook(author.getBooks().getFirst());
    }

    public void deleteAuthor(Long authorId) {
        em.remove(em.find(Author.class, authorId));   // cascades to the author's books
    }

    // ---------- the persistence context and fetching ----------

    /** FIRST-LEVEL CACHE: inside one transaction, the same id gives the SAME object and one SELECT. */
    @Transactional(readOnly = true)
    public QueryReport findTwiceInOneTransaction(Long id) {
        em.clear();
        stats.clear();
        Author first = em.find(Author.class, id);
        Author second = em.find(Author.class, id);
        return new QueryReport("same instance: " + (first == second), stats.getPrepareStatementCount());
    }

    /** THE N+1 PROBLEM: 1 query for the books + 1 query per book to load its lazy author. */
    @Transactional(readOnly = true)
    public QueryReport titlesWithAuthorsNaive() {
        em.clear();
        stats.clear();
        List<String> lines = em.createQuery("SELECT b FROM Book b ORDER BY b.id", Book.class).getResultList()
                .stream().map(b -> b.getTitle() + " by " + b.getAuthor().getName()).toList();
        return new QueryReport(lines.toString(), stats.getPrepareStatementCount());
    }

    /** THE FIX: JOIN FETCH loads books and authors in ONE query. */
    @Transactional(readOnly = true)
    public QueryReport titlesWithAuthorsJoinFetch() {
        em.clear();
        stats.clear();
        List<String> lines = em.createQuery("SELECT b FROM Book b JOIN FETCH b.author ORDER BY b.id", Book.class).getResultList()
                .stream().map(b -> b.getTitle() + " by " + b.getAuthor().getName()).toList();
        return new QueryReport(lines.toString(), stats.getPrepareStatementCount());
    }

    public record QueryReport(String result, long sqlStatements) {
    }
}
