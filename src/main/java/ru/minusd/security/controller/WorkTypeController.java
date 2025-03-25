package ru.minusd.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface WorkTypeController {
    ResponseEntity<?> findTypeWorkById(@PathVariable Long id);
    ResponseEntity<?> findAllTypesWork();
}
