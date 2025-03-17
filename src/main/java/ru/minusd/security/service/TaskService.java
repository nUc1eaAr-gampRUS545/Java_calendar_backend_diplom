package ru.minusd.security.service;

import ru.minusd.security.domain.dto.request.AddFilesInTaskRequest;
import ru.minusd.security.domain.dto.request.TaskCreateRequest;
import ru.minusd.security.domain.dto.TaskDto;
import ru.minusd.security.domain.model.Task;

public interface TaskService {
    TaskDto save(TaskCreateRequest request);
    Task findById(Long id);
    void deleteById(Long id);
    void addFilesInTask(AddFilesInTaskRequest request);

}
