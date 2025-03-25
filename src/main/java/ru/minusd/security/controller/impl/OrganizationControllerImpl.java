package ru.minusd.security.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.minusd.security.controller.OrganizationController;
import ru.minusd.security.domain.dto.response.ErrorResponse;
import ru.minusd.security.domain.dto.response.SuccessResponse;
import ru.minusd.security.service.OrganizationService;
@RestController
@RequiredArgsConstructor
@RequestMapping("/organization")
@CrossOrigin("*")
@Tag(name = "Организации")
public class OrganizationControllerImpl implements OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping()
    @Operation(summary = "Получить все организации")
    public ResponseEntity<?> findAllOrganizations() {
        try {
            return ResponseEntity.ok(SuccessResponse.builder().success(true).message("Организации успешно получены")
                    .data(organizationService.findAll()).build());

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                    .success(false).status(HttpStatus.NOT_FOUND.value()).message("Не удалось найти организации").details(e.getMessage()).build());
        }
    }
}
