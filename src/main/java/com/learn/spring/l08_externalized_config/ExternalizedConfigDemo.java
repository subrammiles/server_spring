package com.learn.spring.l08_externalized_config;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class ExternalizedConfigDemo {

    public record Product(String name, double price) {
    }

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(ConfigConfig.class)) {
            AppSettings s = context.getBean(AppSettings.class);
            System.out.println("--- @Value ---");
            System.out.println("name         = " + s.getName());
            System.out.println("maxUsers     = " + s.getMaxUsers());
            System.out.println("region       = " + s.getRegion() + "   (default, not in the file)");
            System.out.println("features     = " + Arrays.toString(s.getFeatures()));
            System.out.println("maxSessions  = " + s.getMaxSessions() + "   (SpEL: max-users * 2)");
            System.out.println("javaVersion  = " + s.getJavaVersion());
            System.out.println("welcome file = " + s.welcomeText());

            System.out.println("\n--- SpEL referencing another bean ---");
            System.out.println("100.0 after discount = " + context.getBean(PricingService.class).priceAfterDiscount(100.0));

            System.out.println("\n--- Environment ---");
            EnvironmentReader env = context.getBean(EnvironmentReader.class);
            System.out.println("admin email   = " + env.adminEmail());
            System.out.println("max users     = " + env.maxUsers());
            System.out.println("support phone = " + env.supportPhone());

            System.out.println("\n--- i18n with MessageSource ---");
            System.out.println(context.getMessage("greeting", new Object[]{"Subbu"}, Locale.ENGLISH));
            System.out.println(context.getMessage("greeting", new Object[]{"Subbu"}, Locale.FRENCH));
            System.out.println(context.getMessage("cart.items", new Object[]{3}, Locale.FRENCH));
        }

        System.out.println("\n--- SpEL on its own (no container needed) ---");
        ExpressionParser parser = new SpelExpressionParser();
        System.out.println(parser.parseExpression("'spring'.toUpperCase() + ' ' + (2 + 3)").getValue());
        List<Product> products = List.of(new Product("pen", 2), new Product("book", 15), new Product("lamp", 40));
        // .?[...] = selection (filter), .![...] = projection (map)
        Object expensive = parser.parseExpression("#root.?[price() > 10].![name()]").getValue(products);
        System.out.println("products over 10: " + expensive);
    }
}
