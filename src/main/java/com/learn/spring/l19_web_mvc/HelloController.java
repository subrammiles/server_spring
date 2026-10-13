package com.learn.spring.l19_web_mvc;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** The different places a request can carry data. */
@RestController
public class HelloController {

    /** Query parameter with a default: /hello?name=Subbu */
    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "world") String name) {
        return "Hello, " + name + "!";
    }

    /** Path variable: /users/42/greeting */
    @GetMapping("/users/{userId}/greeting")
    public Map<String, Object> greetUser(@PathVariable long userId,
                                         @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        return Map.of("userId", userId, "language", language, "message", "Welcome back");
    }
}
