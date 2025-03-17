package ru.minusd.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import ru.minusd.security.domain.model.FileInfo;
import ru.minusd.security.repository.FileRepository;
import ru.minusd.security.service.FileService;
import ru.minusd.security.utils.FileManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileManager fileManager;

    @Transactional(rollbackFor = {IOException.class})
    @Override
    public FileInfo upload(MultipartFile resource) throws IOException {

        String key = generateKey(resource.getOriginalFilename());

        FileInfo createdFile = FileInfo.builder()
                .name(resource.getOriginalFilename())
                .key(key)
                .size(resource.getSize())
                .build();

        createdFile = fileRepository.save(createdFile).orElseThrow(
                FileNotFoundException::new);
        fileManager.upload(resource.getBytes(), resource.getOriginalFilename());

        return createdFile;
    }
    @Override
    public Resource download(String key) throws IOException {
        return fileManager.download(key);
    }

    @Transactional(readOnly = true)
    @Override
    public FileInfo findById(Long fileId) throws FileNotFoundException {
        return fileRepository.findById(fileId).orElseThrow(
                () -> new FileNotFoundException(fileId.toString()));
    }

    @Transactional(rollbackFor = {IOException.class})
    @Override
    public void delete(Long fileId) throws IOException {
        FileInfo file = fileRepository.findById(fileId).orElseThrow(
                () -> new FileNotFoundException(fileId.toString()));
        fileRepository.deleteById(fileId);
        fileManager.delete(file.getName());
    }

    private String generateKey(String name) {
        return DigestUtils.md5Hex(name + LocalDateTime.now().toString());
    }
}
