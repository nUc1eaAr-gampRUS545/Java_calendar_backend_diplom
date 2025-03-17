package ru.minusd.security.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.minusd.security.controller.TaskController;
import ru.minusd.security.domain.dto.request.AddFilesInTaskRequest;
import ru.minusd.security.domain.dto.response.ErrorResponse;
import ru.minusd.security.domain.dto.response.SuccessResponse;
import ru.minusd.security.domain.dto.request.TaskCreateRequest;
import ru.minusd.security.domain.dto.TaskDto;
import ru.minusd.security.domain.model.Task;
import ru.minusd.security.mapper.impl.TaskMapper;
import ru.minusd.security.service.TaskService;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Задачи")
public class TaskControllerImpl implements TaskController {

    private final TaskService taskService;
    private static final Logger logger = LoggerFactory.getLogger(TaskControllerImpl.class);

    @Override
    @PostMapping("")
    @Operation(summary = "Создание задачи")
    public ResponseEntity<?> createTask(@RequestBody @Valid TaskCreateRequest request) {
        try {
            TaskDto createdTask = taskService.save(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.builder().success(true).message("Задача успешно создана").data(createdTask).build());
        } catch (RuntimeException e) {
            return catchException(e,"Ошибка при создании задачи");
        }
    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Найти задачу по id")
    public ResponseEntity<?> findTaskById(@PathVariable Long id) {
        try {
            Task foundTask = taskService.findById(id);
            return ResponseEntity.ok(SuccessResponse.builder().success(true).message("Задача найдена").data(foundTask).build());
        } catch (RuntimeException e) {
            return catchException(e,"Ошибка при поиске задачи id "+id);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить задачу")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            taskService.deleteById(id);
            return ResponseEntity.ok(SuccessResponse.builder().success(true).message("Задача удалена").build());
        } catch (RuntimeException e) {
            return catchException(e,"Ошибка при удалении задачи");
        }
    }

    @PatchMapping("/add_files")
    @Operation(summary = "Добавление файлов в задачу")
    public ResponseEntity<?> addFilesInTask(@RequestBody @Valid AddFilesInTaskRequest request) {
        try {
             taskService.addFilesInTask(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(SuccessResponse.builder().success(true).message("К задаче успешно добавлены файлы "+request.getFilesIds()).data(null).build());
        } catch (RuntimeException e) {
            return catchException(e,"Ошибка при добавлении файлов");
        }
    }
    private ResponseEntity<ErrorResponse> catchException(RuntimeException e, String message) {
        logger.error(message,e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),message,e.getMessage()));
    }
}
