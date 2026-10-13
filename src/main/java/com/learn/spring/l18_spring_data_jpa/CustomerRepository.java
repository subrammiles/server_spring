package com.learn.spring.l18_spring_data_jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * An INTERFACE only. Spring Data generates the implementation at startup.
 * JpaRepository already gives: save, saveAll, findById, findAll, findAll(Sort/Pageable),
 * count, existsById, deleteById, delete, flush...
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // ---- derived queries: the query is generated from the METHOD NAME ----

    List<Customer> findByCity(String city);

    Optional<Customer> findByEmailIgnoreCase(String email);

    List<Customer> findByAgeBetweenOrderByAgeAsc(int min, int max);

    List<Customer> findByNameStartingWith(String prefix);

    long countByActiveTrue();

    boolean existsByEmail(String email);

    // ---- paging ----

    Page<Customer> findByActiveTrue(Pageable pageable);

    // ---- projections ----

    List<CustomerSummary> findByCityOrderByName(String city);

    @Query("SELECT new com.learn.spring.l18_spring_data_jpa.CityCount(c.city, COUNT(c)) FROM Customer c GROUP BY c.city ORDER BY c.city")
    List<CityCount> countPerCity();

    // ---- explicit queries when the method name would get too long ----

    @Query("SELECT c FROM Customer c WHERE c.active = true AND c.age >= :minAge ORDER BY c.name")
    List<Customer> findActiveAtLeast(@Param("minAge") int minAge);

    @Query(value = "SELECT * FROM customer WHERE city = ?1 ORDER BY name", nativeQuery = true)
    List<Customer> findByCityNative(String city);

    /** Bulk UPDATE/DELETE needs @Modifying and a transaction. Bypasses dirty checking and auditing! */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Customer c SET c.active = false WHERE c.city = :city")
    int deactivateAllIn(@Param("city") String city);
}
