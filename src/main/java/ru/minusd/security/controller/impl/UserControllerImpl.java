package ru.minusd.security.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.minusd.security.controller.UserController;
import ru.minusd.security.domain.dto.response.ErrorResponse;
import ru.minusd.security.domain.dto.response.SuccessResponse;
import ru.minusd.security.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Пользователи")
public class UserControllerImpl implements UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);
    private final UserService userService;

    @GetMapping("/{id}")
    @Override
    @Operation(summary = "Найти пользователя")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        try {
            if (id==null) {
                return ResponseEntity.badRequest().body(ErrorResponse.builder().success(false).status(HttpStatus.BAD_REQUEST.value()).message("Ошибка валидации: ID не может быть null").build());
            }

            var user = userService.findById(id);
            return ResponseEntity.ok(SuccessResponse.builder().success(true).message("Пользователь найден").data(user).build());
        } catch (RuntimeException e) {
            logger.error("Ошибка при поиске пользователя: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().success(false).status(HttpStatus.NOT_FOUND.value()).message("Пользователь не найден").details(e.getMessage()).build());
        }
    }

    @GetMapping("/{id}/tasks")
    @Operation(summary = "Найти задачи пользователя")
    public ResponseEntity<?> findUserTasksById(@PathVariable Long id) {
        try {
            if (id==null) {
                return ResponseEntity.badRequest().body(ErrorResponse.builder().success(false).status(HttpStatus.BAD_REQUEST.value()).message("Ошибка валидации: ID не может быть null").build());
            }

            var tasks = userService.findUserTasksById(id);
            return ResponseEntity.ok(SuccessResponse.builder().success(true).message("Задачи пользователя " + id).data(tasks).build());

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().success(false).status(HttpStatus.NOT_FOUND.value()).message("Не удалось найти задачи пользователя").details(e.getMessage()).build());
        }
    }
}
