package com.learn.spring.l19_web_mvc;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

/** In-memory storage keeps the lesson about the web layer. Swap in lesson 18's repository for a real DB. */
@Service
public class TaskService {
    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong ids = new AtomicLong();

    public List<Task> findAll(Boolean done) {
        return tasks.values().stream()
                .filter(t -> done == null || t.done() == done)
                .sorted(Comparator.comparingLong(Task::id))
                .toList();
    }

    public Task get(long id) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new TaskNotFoundException(id);
        }
        return task;
    }

    public Task create(TaskRequest request) {
        long id = ids.incrementAndGet();
        Task task = new Task(id, request.title(), request.isDone());
        tasks.put(id, task);
        return task;
    }

    public Task update(long id, TaskRequest request) {
        get(id);
        Task task = new Task(id, request.title(), request.isDone());
        tasks.put(id, task);
        return task;
    }

    public void delete(long id) {
        if (tasks.remove(id) == null) {
            throw new TaskNotFoundException(id);
        }
    }
}
