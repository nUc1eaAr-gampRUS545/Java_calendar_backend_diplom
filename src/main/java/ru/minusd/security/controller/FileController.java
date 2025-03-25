package ru.minusd.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface FileController {
    ResponseEntity<?> upload(@RequestParam("attachment") MultipartFile attachment);
    ResponseEntity<?> download(@PathVariable("id") Long id);
    ResponseEntity<Void> delete(@PathVariable("id") Long id);
}
