package ru.minusd.security.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.minusd.security.controller.AuthController;
import ru.minusd.security.domain.dto.*;
import ru.minusd.security.domain.dto.request.SignInRequest;
import ru.minusd.security.domain.dto.request.SignUpRequest;
import ru.minusd.security.domain.dto.response.ErrorResponse;
import ru.minusd.security.domain.dto.response.SuccessResponse;
import ru.minusd.security.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthControllerImpl implements AuthController {
    private final AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(AuthControllerImpl.class);

    /**
     * @param request
     * @return
     */
    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.builder()
                    .success(true).message("Пользователь успешно создан")
                    .data(authenticationService.signUp(request)).build());
        }
        catch (RuntimeException e){
            logger.error("Ошибка при создании пользователя: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder()
                    .success(false).status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка при создании пользователя").details(e.getMessage()).build());
        }

    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}

