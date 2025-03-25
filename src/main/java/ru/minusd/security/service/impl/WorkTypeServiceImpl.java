package ru.minusd.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.minusd.security.domain.entity.WorkType;
import ru.minusd.security.repository.WorkTypeRepository;
import ru.minusd.security.service.WorkTypeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkTypeServiceImpl implements WorkTypeService {

    private final WorkTypeRepository repository;

    public List<WorkType> findAll() {
        return repository.findAll().orElseThrow(
                () -> new RuntimeException("Типы работ не найдены")
        );
    }

    public WorkType findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Тип работы не найден")
        );
    }
}
