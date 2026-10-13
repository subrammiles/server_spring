package com.learn.spring.l19_web_mvc;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(long id) {
        super("Task " + id + " not found");
    }
}
