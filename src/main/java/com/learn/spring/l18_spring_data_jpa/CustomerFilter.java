package com.learn.spring.l18_spring_data_jpa;

/** A search form where EVERY field is optional: null means "don't filter on this". */
public record CustomerFilter(String nameContains, String city, Integer minAge, Boolean active) {
}
