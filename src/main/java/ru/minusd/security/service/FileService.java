package ru.minusd.security.service;

import org.springframework.web.multipart.MultipartFile;
import ru.minusd.security.domain.entity.FileInfo;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {

    FileInfo upload(MultipartFile resource) throws IOException;
    void delete(Long fileId) throws IOException;
    String download(String path) throws IOException;
    FileInfo findById(Long fileId) throws FileNotFoundException;
}