package com.learn.spring.l17_jpa_hibernate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.learn.spring.l17_jpa_hibernate.entity.Author;
import com.learn.spring.l17_jpa_hibernate.entity.Book;

/** @DirtiesContext throws the context (and its in-memory DB) away after each test: a clean DB every time. */
@SpringJUnitConfig(JpaConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LibraryServiceTest {

    @Autowired
    LibraryService library;

    Long tolkienId;

    @BeforeEach
    void seed() {
        tolkienId = library.seedSampleData();
    }

    @Test
    void jpqlQueriesAndProjections() {
        assertThat(library.findBooksByAuthorName("Tolkien")).extracting(Book::getTitle)
                .containsExactly("The Hobbit", "The Lord of the Rings");
        assertThat(library.bookCountPerAuthor()).contains(new AuthorBookCount("Joshua Bloch", 2));
        assertThat(library.titlesTagged("classic")).containsExactly("The Hobbit", "The Lord of the Rings");
        assertThat(library.countBooksWithNativeSql()).isEqualTo(5);
    }

    @Test
    void dirtyCheckingSavesChangesWithoutCallingSave() {
        library.renameAuthor(tolkienId, "J.R.R. Tolkien");
        assertThat(library.findAuthor(tolkienId).getName()).isEqualTo("J.R.R. Tolkien");
    }

    @Test
    void lazyCollectionCannotBeReadAfterTheTransaction() {
        Author detached = library.findAuthor(tolkienId);
        assertThatThrownBy(() -> detached.getBooks().size()).isInstanceOf(LazyInitializationException.class);
    }

    @Test
    void firstLevelCacheAndNPlusOne() {
        assertThat(library.findTwiceInOneTransaction(tolkienId).sqlStatements()).isEqualTo(1);
        // 1 query for the books + 1 per distinct author (3 authors)
        assertThat(library.titlesWithAuthorsNaive().sqlStatements()).isEqualTo(4);
        assertThat(library.titlesWithAuthorsJoinFetch().sqlStatements()).isEqualTo(1);
    }

    @Test
    void orphanRemovalAndCascadeDelete() {
        library.removeFirstBook(tolkienId);
        assertThat(library.countBooksWithNativeSql()).isEqualTo(4);
        library.deleteAuthor(tolkienId);
        assertThat(library.countBooksWithNativeSql()).isEqualTo(3);
    }
}
