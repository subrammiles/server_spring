package com.learn.spring.l18_spring_data_jpa;

/** Interface projection: Spring Data SELECTs only these columns and implements the interface for you. */
public interface CustomerSummary {
    String getName();

    String getCity();
}
