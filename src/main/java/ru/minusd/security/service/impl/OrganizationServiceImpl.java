package ru.minusd.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.minusd.security.domain.dto.OrganizationDto;
import ru.minusd.security.domain.entity.Organization;
import ru.minusd.security.mapper.impl.OrganizationMapper;
import ru.minusd.security.repository.OrganizationRepository;
import ru.minusd.security.service.OrganizationService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository repository;
    private final OrganizationMapper mapper = new OrganizationMapper();

    @Override
    @Transactional
    public Organization findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Организация не найдена: " + id));
    }

    @Override
    @Transactional
    public List<OrganizationDto> findAll() {
        List<Organization> organizations = repository.findAll()
                .orElseThrow(() -> new IllegalArgumentException("Организации не найдены"));
        List<OrganizationDto> dtos = new ArrayList<>();
        organizations.forEach(organization -> {
            dtos.add(mapper.map(organization));
        });
        return dtos;
    }

}
