package ru.minusd.security.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.minusd.security.domain.dto.JwtAuthenticationResponse;
import ru.minusd.security.domain.dto.UserDto;
import ru.minusd.security.domain.dto.request.SignInRequest;
import ru.minusd.security.domain.dto.request.SignUpRequest;

public interface AuthenticationService {

    JwtAuthenticationResponse signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);

    UserDto checkVerifyToken(HttpServletRequest request);
}