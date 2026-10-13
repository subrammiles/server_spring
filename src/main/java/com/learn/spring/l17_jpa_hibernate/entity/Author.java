package com.learn.spring.l17_jpa_hibernate.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/** @Entity = this class maps to a table; each instance is one row. */
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // the database generates the key
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Embedded
    private Address address;

    /**
     * One author -> many books. mappedBy = "author": the Book side owns the foreign key.
     * cascade = ALL: persisting/removing the author does the same to its books.
     * orphanRemoval: a book removed from this list is DELETED.
     * Collections are LAZY by default: loaded only when first accessed.
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    /** JPA needs a no-arg constructor; protected keeps application code from using it. */
    protected Author() {
    }

    public Author(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    /** Keep BOTH sides of a bidirectional relationship in sync in one place. */
    public Book addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
        return book;
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setAuthor(null);
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Address getAddress() { return address; }
    public List<Book> getBooks() { return Collections.unmodifiableList(books); }

    /** Never touch lazy collections in toString(): it would trigger queries or exceptions. */
    @Override
    public String toString() {
        return "Author{id=" + id + ", name='" + name + "', address=" + address + "}";
    }
}
