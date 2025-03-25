package ru.minusd.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.minusd.security.domain.entity.Place;
import ru.minusd.security.repository.PlaceRepository;
import ru.minusd.security.service.PlaceService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository repository;

    @Override
    @Transactional
    public Place findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Организация не найдена: " + id));
    }

    @Override
    @Transactional
    public List<Place> findAll() {
        List<Place> places = repository.findAll()
                .orElseThrow(() -> new IllegalArgumentException("Организации не найдены"));

        return places;
    }
}
