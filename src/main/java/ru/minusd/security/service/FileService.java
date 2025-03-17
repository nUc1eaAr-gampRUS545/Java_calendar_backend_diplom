package ru.minusd.security.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.minusd.security.domain.model.FileInfo;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {

    FileInfo upload(MultipartFile resource) throws IOException;
    void delete(Long fileId) throws IOException;
    Resource download(String key) throws IOException;
    FileInfo findById(Long fileId) throws FileNotFoundException;
}