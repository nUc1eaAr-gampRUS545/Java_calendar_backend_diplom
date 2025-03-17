package ru.minusd.security.service;

import ru.minusd.security.domain.model.Organization;

public interface OrganizationService {
    Organization findById(Long id);
}
