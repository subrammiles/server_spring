package com.learn.spring.l19_web_mvc;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 * FILE UPLOAD AND DOWNLOAD.
 *   POST /api/files           multipart/form-data with a "file" part -> 201 + metadata
 *   GET  /api/files/{name}    the bytes, with Content-Type and Content-Disposition: attachment
 *
 * Multipart needs two things (both in this lesson): a MultipartConfigElement on the servlet
 * (EmbeddedTomcatServer, also sets the size limits) and a MultipartResolver bean (WebConfig).
 * Files are kept in memory here. Real apps stream them to disk or object storage (S3) and
 * store only the metadata in the database.
 */
@RestController
@RequestMapping("/api/files")
public class FileController {

    record StoredFile(String contentType, byte[] content) {
    }

    public record FileInfo(String name, String contentType, long size) {
    }

    private final Map<String, StoredFile> files = new ConcurrentHashMap<>();

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileInfo> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty() || file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Choose a non-empty file");
        }
        // NEVER trust the client's file name: "../../etc/passwd" must not become a path. Keep the last segment only.
        String name = Path.of(file.getOriginalFilename()).getFileName().toString();
        String type = file.getContentType() != null ? file.getContentType() : MediaType.APPLICATION_OCTET_STREAM_VALUE;
        files.put(name, new StoredFile(type, file.getBytes()));
        return ResponseEntity.created(URI.create("/api/files/" + name)).body(new FileInfo(name, type, file.getSize()));
    }

    @GetMapping("/{name}")
    public ResponseEntity<byte[]> download(@PathVariable String name) {
        StoredFile file = files.get(name);
        if (file == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File " + name + " not found");
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.contentType()))
                // "attachment" makes browsers save the file instead of displaying it
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(name).build().toString())
                .body(file.content());
    }
}
