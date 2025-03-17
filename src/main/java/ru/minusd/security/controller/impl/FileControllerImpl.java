package ru.minusd.security.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.minusd.security.controller.FileController;
import ru.minusd.security.domain.model.FileInfo;
import ru.minusd.security.service.FileService;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Файлы")
public class FileControllerImpl implements FileController {

    private final FileService fileService;
    private static final Logger logger = LoggerFactory.getLogger(FileControllerImpl.class);

    @Override
    @PostMapping
    @Operation(summary = "Загрузка файла в облако")
    public ResponseEntity<FileInfo> upload(@RequestParam("attachment") MultipartFile attachment) {
        try {
            return new ResponseEntity<>(fileService.upload(attachment),HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    @Operation(summary = "Загрузка файла из облака")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download(@PathVariable("id") Long id) {
        try {
            FileInfo foundFile = fileService.findById(id);
            Resource resource = fileService.download(foundFile.getName());
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + foundFile.getName())
                    .body(resource);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Удаление файла")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            fileService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}