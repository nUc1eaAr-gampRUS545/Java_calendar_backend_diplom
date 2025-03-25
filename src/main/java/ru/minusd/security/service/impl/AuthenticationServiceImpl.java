package ru.minusd.security.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.minusd.security.domain.dto.JwtAuthenticationResponse;
import ru.minusd.security.domain.dto.UserDto;
import ru.minusd.security.domain.dto.request.SignInRequest;
import ru.minusd.security.domain.dto.request.SignUpRequest;
import ru.minusd.security.domain.entity.Organization;
import ru.minusd.security.domain.entity.Role;
import ru.minusd.security.domain.entity.User;
import ru.minusd.security.mapper.impl.UserMapper;
import ru.minusd.security.service.AuthenticationService;
import ru.minusd.security.service.JwtService;
import ru.minusd.security.service.OrganizationService;
import ru.minusd.security.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final OrganizationService organizationService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    //@CachePut(value = "users")
    @Transactional
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        Long organizationId = Long.parseLong(String.valueOf(request.getOrganizationId()));
        Organization organization = organizationService.findById(organizationId);

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .phoneNumber(request.getPhonenumber())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .organizationByUser(organization)
                .build();

        User createUser = userService.create(user);

        var jwt = jwtService.generateToken(createUser);
        return new JwtAuthenticationResponse(createUser.getId(),jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    @Transactional
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());
        Long id = userService.getByUsername(request.getUsername()).getId();
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(id,jwt);
    }
    /**
     * Проверка токена
     *
     * @param request токен
     * @return данные пользователя
     */
    @Transactional
    public UserDto checkVerifyToken(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BadCredentialsException("Invalid or missing Authorization header");
        }

        final String jwt = authHeader.substring(7);

        String username = jwtService.extractUserName(jwt);
        if (username == null) {
            throw new BadCredentialsException("Invalid token");
        }

        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

        if (!jwtService.isTokenValid(jwt, userDetails)) {
            throw new BadCredentialsException("Invalid token");
        }
        return userMapper.map(userService.getByUsername(username));
    }
}
