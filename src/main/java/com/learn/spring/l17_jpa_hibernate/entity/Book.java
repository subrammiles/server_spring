package com.learn.spring.l17_jpa_hibernate.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private int publishedYear;

    /** STRING stores "TECH"; the default ORDINAL stores 1 and breaks if you reorder the enum. */
    @Enumerated(EnumType.STRING)
    private Genre genre;

    /**
     * Many books -> one author. This side owns the author_id foreign key column.
     * @ManyToOne is EAGER by default; LAZY is almost always the better choice.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id")
    private Author author;

    /** Many-to-many needs a join table: book_tags(book_id, tag_id). */
    @ManyToMany
    @JoinTable(name = "book_tags",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    /** Optimistic locking: concurrent updates of the same row fail instead of silently overwriting. */
    @Version
    private Long version;

    protected Book() {
    }

    public Book(String title, int publishedYear, Genre genre) {
        this.title = title;
        this.publishedYear = publishedYear;
        this.genre = genre;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getPublishedYear() { return publishedYear; }
    public Genre getGenre() { return genre; }
    public Author getAuthor() { return author; }
    void setAuthor(Author author) { this.author = author; }
    public Set<Tag> getTags() { return tags; }
    public Long getVersion() { return version; }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', year=" + publishedYear + ", genre=" + genre + "}";
    }
}
