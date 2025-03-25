package ru.minusd.security.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.minusd.security.domain.dto.request.ApplicationCreateRequest;

public interface ApplicationController {
    ResponseEntity<?> createApplication(@RequestBody @Valid ApplicationCreateRequest request);
    ResponseEntity<?> getApplications(@PathVariable Long id);
}
