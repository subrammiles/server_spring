package com.learn.spring.l17_jpa_hibernate;

import org.hibernate.LazyInitializationException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.learn.spring.l17_jpa_hibernate.entity.Author;

public class JpaDemo {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(JpaConfig.class)) {
            LibraryService library = context.getBean(LibraryService.class);

            step("persist with cascade");
            Long tolkienId = library.seedSampleData();

            step("find + JPQL");
            System.out.println(library.findAuthor(tolkienId));
            System.out.println(library.findBooksByAuthorName("Joshua Bloch"));
            System.out.println(library.bookCountPerAuthor());
            System.out.println("tagged 'bestseller': " + library.titlesTagged("bestseller"));
            System.out.println("native SQL count: " + library.countBooksWithNativeSql());

            step("dirty checking: an UPDATE without calling save()");
            library.renameAuthor(tolkienId, "J.R.R. Tolkien");
            System.out.println(library.findAuthor(tolkienId));

            step("LazyInitializationException: touching a lazy collection after the transaction ended");
            Author detached = library.findAuthor(tolkienId);
            try {
                detached.getBooks().size();
            } catch (LazyInitializationException e) {
                System.out.println("-> " + e.getClass().getSimpleName() + ": load what you need INSIDE the transaction (JOIN FETCH)");
            }

            step("first-level cache");
            System.out.println(library.findTwiceInOneTransaction(tolkienId));

            step("N+1 problem vs JOIN FETCH");
            System.out.println("naive      : " + library.titlesWithAuthorsNaive().sqlStatements() + " SQL statements");
            System.out.println("join fetch : " + library.titlesWithAuthorsJoinFetch().sqlStatements() + " SQL statement");

            step("orphanRemoval and cascade delete");
            library.removeFirstBook(tolkienId);
            System.out.println("books left: " + library.countBooksWithNativeSql());
            library.deleteAuthor(tolkienId);
            System.out.println("books left after deleting the author: " + library.countBooksWithNativeSql());
        }
    }

    private static void step(String title) {
        System.out.println("\n=== " + title + " ===");
    }
}
