package com.learn.spring.l19_web_mvc;

/** What the API returns. Jackson turns it into {"id":1,"title":"...","done":false}. */
public record Task(long id, String title, boolean done) {
}
