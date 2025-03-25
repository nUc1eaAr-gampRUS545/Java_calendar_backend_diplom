package ru.minusd.security.service;

import ru.minusd.security.domain.dto.OrganizationDto;
import ru.minusd.security.domain.entity.Organization;

import java.util.List;

public interface OrganizationService {
    Organization findById(Long id);
    List<OrganizationDto> findAll();
}
