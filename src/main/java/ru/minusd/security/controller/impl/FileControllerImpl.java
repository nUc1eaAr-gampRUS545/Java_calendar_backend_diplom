package ru.minusd.security.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.minusd.security.controller.FileController;
import ru.minusd.security.domain.dto.response.ErrorResponse;
import ru.minusd.security.domain.dto.response.SuccessResponse;
import ru.minusd.security.domain.entity.FileInfo;
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
    public ResponseEntity<?> upload(@RequestParam("attachment") MultipartFile attachment) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse(true,"Файл получен",fileService.upload(attachment)));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    @Operation(summary = "Загрузка файла из облака")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> download(@PathVariable("id") Long id) {
        try {
            FileInfo foundFile = fileService.findById(id);
            String resource = fileService.download(foundFile.getName());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse(true,"Файл получен",resource));

        } catch (IOException e) {
            logger.error("Ошибка: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder()
                    .success(false).status(HttpStatus.BAD_REQUEST.value())
                    .message("Ошибка:").details(e.getMessage()).build());
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