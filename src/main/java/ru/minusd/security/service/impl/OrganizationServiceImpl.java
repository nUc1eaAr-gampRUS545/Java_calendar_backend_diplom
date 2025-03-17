package ru.minusd.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.minusd.security.domain.model.Organization;
import ru.minusd.security.repository.OrganizationRepository;
import ru.minusd.security.service.OrganizationService;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository repository;

    @Override
    @Transactional
    public Organization findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Organization not found: " + id));
    }

}
