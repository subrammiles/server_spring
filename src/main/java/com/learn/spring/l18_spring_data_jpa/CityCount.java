package com.learn.spring.l18_spring_data_jpa;

/** DTO projection filled by a JPQL constructor expression. */
public record CityCount(String city, long customers) {
}
