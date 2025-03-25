package ru.minusd.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import ru.minusd.security.domain.dto.JwtAuthenticationResponse;
import ru.minusd.security.domain.dto.request.SignInRequest;
import ru.minusd.security.domain.dto.request.SignUpRequest;

public interface AuthController {
    ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest request);
    ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest request);
    ResponseEntity<?> verifyToken(HttpServletRequest request);
}
