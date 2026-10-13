package com.learn.spring.l17_jpa_hibernate.entity;

import jakarta.persistence.Embeddable;

/** @Embeddable: a value object stored as columns of the OWNING table (no table of its own). */
@Embeddable
public class Address {
    private String city;
    private String country;

    protected Address() {
    }

    public Address(String city, String country) {
        this.city = city;
        this.country = country;
    }

    public String getCity() { return city; }
    public String getCountry() { return country; }

    @Override
    public String toString() {
        return city + ", " + country;
    }
}
