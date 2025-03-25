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
import ru.minusd.security.controller.ApplicationController;
import ru.minusd.security.domain.dto.ApplicationDto;
import ru.minusd.security.domain.dto.TaskDto;
import ru.minusd.security.domain.dto.request.ApplicationCreateRequest;
import ru.minusd.security.domain.dto.request.TaskCreateRequest;
import ru.minusd.security.domain.dto.response.ErrorResponse;
import ru.minusd.security.domain.dto.response.SuccessResponse;
import ru.minusd.security.service.ApplicationService;
import ru.minusd.security.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Заявки на пропуск")
public class ApplicationControllerImpl implements ApplicationController {
    private final ApplicationService applicationService;

    private static final Logger logger = LoggerFactory.getLogger(ApplicationControllerImpl.class);

    @Override
    @PostMapping
    @Operation(summary = "Создание заявки")
    public ResponseEntity<?> createApplication(@RequestBody @Valid ApplicationCreateRequest request) {
        try {
            ApplicationDto createdApplication = applicationService.createApplication(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse(true,"Задача успешно создана",createdApplication));
        } catch (RuntimeException e) {
            return catchException(e,"Ошибка при создании заявки: " + e.getMessage());

        }
    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Получить заявки")
    public ResponseEntity<?> getApplications(@PathVariable Long id) {
        try {
            List<ApplicationDto> applications = applicationService.findAllApplications(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SuccessResponse(true,"Задача успешно получены",applications));
        } catch (RuntimeException e) {
            return catchException(e,"Ошибка при получении заявок: " + e.getMessage());
        }
    }

    private ResponseEntity<ErrorResponse> catchException(RuntimeException e,String message) {
        logger.error(message,e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder()
                .success(false).status(HttpStatus.BAD_REQUEST.value())
                .message(message).details(e.getMessage()).build());
    }


}
