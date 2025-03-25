package ru.minusd.security.service;

import ru.minusd.security.domain.entity.Place;

import java.util.List;

public interface PlaceService {
    Place findById(Long id);
    List<Place> findAll();
}
