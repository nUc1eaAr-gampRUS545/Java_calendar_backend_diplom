package ru.minusd.security.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.minusd.security.domain.model.FileInfo;

public interface FileController {
    ResponseEntity<FileInfo> upload(@RequestParam("attachment") MultipartFile attachment);
    ResponseEntity<Resource> download(@PathVariable("id") Long id);
    ResponseEntity<Void> delete(@PathVariable("id") Long id);
}
