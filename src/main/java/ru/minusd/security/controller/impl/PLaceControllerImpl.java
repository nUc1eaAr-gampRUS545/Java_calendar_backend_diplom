package ru.minusd.security.controller.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.minusd.security.controller.PlaceController;
import ru.minusd.security.domain.dto.response.ErrorResponse;
import ru.minusd.security.domain.dto.response.SuccessResponse;
import ru.minusd.security.service.PlaceService;


@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Места")
public class PLaceControllerImpl implements PlaceController {
    private final PlaceService placeService;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllPlaces() {
        try {
            return ResponseEntity.ok(SuccessResponse.builder().success(true).message("Локации успешно получены")
                    .data(placeService.findAll()).build());

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                    .success(false).status(HttpStatus.NOT_FOUND.value()).message("Не удалось найти локации").details(e.getMessage()).build());
        }
    }


}
