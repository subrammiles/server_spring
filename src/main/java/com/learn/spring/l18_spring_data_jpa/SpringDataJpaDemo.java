package com.learn.spring.l18_spring_data_jpa;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class SpringDataJpaDemo {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(DataJpaConfig.class)) {
            CustomerRepository repo = context.getBean(CustomerRepository.class);
            System.out.println("Repository implementation generated at runtime: " + repo.getClass().getSimpleName());

            repo.saveAll(List.of(
                    new Customer("Anil", "anil@mail.com", "Hyderabad", 34),
                    new Customer("Bhavana", "bhavana@mail.com", "Bengaluru", 27),
                    new Customer("Chetan", "chetan@mail.com", "Hyderabad", 45),
                    new Customer("Deepa", "deepa@mail.com", "Chennai", 19),
                    new Customer("Esha", "esha@mail.com", "Bengaluru", 52),
                    new Customer("Farhan", "farhan@mail.com", "Hyderabad", 23)));

            System.out.println("\n--- built-in CRUD ---");
            System.out.println("count                : " + repo.count());
            System.out.println("findById(1)          : " + repo.findById(1L).orElseThrow());
            System.out.println("sorted by age desc   : " + repo.findAll(Sort.by(Sort.Direction.DESC, "age")));

            System.out.println("\n--- derived queries ---");
            System.out.println("findByCity           : " + repo.findByCity("Hyderabad"));
            System.out.println("findByEmailIgnoreCase: " + repo.findByEmailIgnoreCase("DEEPA@MAIL.COM").orElseThrow());
            System.out.println("age 20..40          : " + repo.findByAgeBetweenOrderByAgeAsc(20, 40));
            System.out.println("existsByEmail        : " + repo.existsByEmail("nobody@mail.com"));

            System.out.println("\n--- paging: page size 2 ---");
            Page<Customer> page = repo.findByActiveTrue(PageRequest.of(0, 2, Sort.by("name")));
            while (true) {
                System.out.println("page " + page.getNumber() + "/" + (page.getTotalPages() - 1) + ": " + page.getContent());
                if (!page.hasNext()) {
                    break;
                }
                page = repo.findByActiveTrue(page.nextPageable());
            }

            System.out.println("\n--- projections ---");
            repo.findByCityOrderByName("Bengaluru").forEach(s -> System.out.println("summary: " + s.getName() + " @ " + s.getCity()));
            System.out.println("per city: " + repo.countPerCity());

            System.out.println("\n--- @Query ---");
            System.out.println("active, 30+ : " + repo.findActiveAtLeast(30));
            System.out.println("native      : " + repo.findByCityNative("Chennai"));
            System.out.println("deactivated : " + repo.deactivateAllIn("Hyderabad") + ", active now: " + repo.countByActiveTrue());

            System.out.println("\n--- auditing ---");
            Customer c = repo.findByEmailIgnoreCase("anil@mail.com").orElseThrow();
            System.out.println("createdAt = " + c.getCreatedAt() + ", updatedAt = " + c.getUpdatedAt());
        }
    }
}
