package ru.minusd.security.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.minusd.security.controller.WorkTypeController;
import ru.minusd.security.domain.dto.response.ErrorResponse;
import ru.minusd.security.domain.dto.response.SuccessResponse;
import ru.minusd.security.service.WorkTypeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("work_type")
@Tag(name = "Типы работ")
public class WorkTypeControllerImpl implements WorkTypeController {

    private static final Logger logger = LoggerFactory.getLogger(WorkTypeControllerImpl.class);
    private final WorkTypeService service;

    @GetMapping("/{id}")
    @Override
    @Operation(summary = "Найти тип работы")
    public ResponseEntity<?> findTypeWorkById(@PathVariable Long id) {
        try {
            if (id==null) {
                return ResponseEntity.badRequest().body(ErrorResponse.builder().success(false).status(HttpStatus.BAD_REQUEST.value()).message("Ошибка валидации: ID не может быть null").build());
            }
            var type = service.findById(id);
            return ResponseEntity.ok(SuccessResponse.builder().success(true).message("Тип найден").data(type).build());
        } catch (RuntimeException e) {
            logger.error("Ошибка при поиске типа: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().success(false).status(HttpStatus.NOT_FOUND.value()).message("Пользователь не найден").details(e.getMessage()).build());
        }
    }
    @GetMapping
    @Override
    @Operation(summary = "Найти все типы")
    public ResponseEntity<?> findAllTypesWork() {
        try {
            return ResponseEntity.ok(SuccessResponse.builder().success(true).message("Типы успешно получены")
                    .data(service.findAll()).build());

        } catch (RuntimeException e) {
            logger.error("Ошибка при поиске типов: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                    .success(false).status(HttpStatus.NOT_FOUND.value()).message("Не удалось найти типы").details(e.getMessage()).build());
        }
    }

}
