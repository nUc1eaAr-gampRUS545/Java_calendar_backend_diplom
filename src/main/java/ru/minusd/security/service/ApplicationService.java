package ru.minusd.security.service;

import ru.minusd.security.domain.dto.ApplicationDto;
import ru.minusd.security.domain.dto.request.ApplicationCreateRequest;

import java.util.List;

public interface ApplicationService {
    ApplicationDto createApplication(ApplicationCreateRequest request);
    List<ApplicationDto> findAllApplications(Long id);
}
