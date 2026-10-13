package com.learn.spring.l17_jpa_hibernate;

/** A DTO filled directly by a JPQL "select new ..." query: no entities, just the data needed. */
public record AuthorBookCount(String author, long books) {
}
