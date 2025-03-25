package ru.minusd.security.service;

import ru.minusd.security.domain.entity.WorkType;

import java.util.List;

public interface WorkTypeService {
    List<WorkType> findAll();
    WorkType findById(Long id);
}
