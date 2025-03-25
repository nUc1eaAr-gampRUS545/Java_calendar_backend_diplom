package ru.minusd.security.service;

import ru.minusd.security.domain.dto.request.AddFilesInTaskRequest;
import ru.minusd.security.domain.dto.request.TaskCreateRequest;
import ru.minusd.security.domain.dto.TaskDto;
import ru.minusd.security.domain.dto.request.TaskUpdateRequest;

public interface TaskService {
    TaskDto save(TaskCreateRequest request);
    TaskDto updateTask(TaskUpdateRequest request);
    TaskDto findById(Long id);
    void deleteById(Long id);
    void addFilesInTask(AddFilesInTaskRequest request);

}
