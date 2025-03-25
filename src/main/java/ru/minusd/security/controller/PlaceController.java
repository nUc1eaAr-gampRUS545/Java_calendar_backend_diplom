package ru.minusd.security.controller;

import org.springframework.http.ResponseEntity;

public interface PlaceController {
    ResponseEntity<?> findAllPlaces();
}
