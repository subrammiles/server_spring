package com.learn.spring.l19_web_mvc;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * A REST resource. @RestController = @Controller + @ResponseBody: return values are written
 * to the response body as JSON instead of being treated as view names.
 *
 *   GET    /api/tasks          list (optional ?done=true|false)
 *   GET    /api/tasks/{id}     one task, 404 if missing
 *   POST   /api/tasks          create, 201 + Location header
 *   PUT    /api/tasks/{id}     replace
 *   DELETE /api/tasks/{id}     delete, 204 No Content
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<Task> list(@RequestParam(required = false) Boolean done) {
        return service.findAll(done);
    }

    @GetMapping("/{id}")
    public Task get(@PathVariable long id) {
        return service.get(id);
    }

    /** @Valid: Bean Validation (lesson 12) runs on the JSON body; failures become HTTP 400. */
    @PostMapping
    public ResponseEntity<Task> create(@Valid @RequestBody TaskRequest request) {
        Task created = service.create(request);
        return ResponseEntity.created(URI.create("/api/tasks/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable long id, @Valid @RequestBody TaskRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
