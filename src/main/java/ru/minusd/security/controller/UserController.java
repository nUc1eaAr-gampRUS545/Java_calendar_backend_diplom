package ru.minusd.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserController {
    ResponseEntity<?> findUserById(@PathVariable Long id);
    ResponseEntity<?> findUserTasksById(@PathVariable Long id);
}
