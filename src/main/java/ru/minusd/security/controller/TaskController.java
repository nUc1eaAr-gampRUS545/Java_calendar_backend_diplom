package ru.minusd.security.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.minusd.security.domain.dto.request.AddFilesInTaskRequest;
import ru.minusd.security.domain.dto.request.TaskCreateRequest;
import ru.minusd.security.domain.dto.request.TaskUpdateRequest;

public interface TaskController {
    ResponseEntity<?> createTask(@RequestBody @Valid TaskCreateRequest request );
    ResponseEntity<?> updateTask(@RequestBody @Valid TaskUpdateRequest request);
    ResponseEntity<?> findTaskById(@PathVariable("id") Long id);
    ResponseEntity<?> delete(@PathVariable("id") Long id);
    ResponseEntity<?> addFilesInTask(@RequestBody @Valid AddFilesInTaskRequest request);
}
