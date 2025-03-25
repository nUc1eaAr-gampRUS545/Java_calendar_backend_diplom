package ru.minusd.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.minusd.security.domain.dto.ApplicationDto;
import ru.minusd.security.domain.dto.request.ApplicationCreateRequest;
import ru.minusd.security.domain.entity.*;
import ru.minusd.security.mapper.impl.ApplicationMapper;
import ru.minusd.security.repository.*;
import ru.minusd.security.service.ApplicationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final FileRepository fileRepository;
    private final WorkTypeRepository workTypeRepository;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final ApplicationMapper applicationMapper;

    @Override
    @Transactional
    public ApplicationDto createApplication(ApplicationCreateRequest request) {
        Set<FileInfo> files = fileRepository.findByFileIds(request.getFileIds())
                .orElseThrow(() -> new IllegalArgumentException("Invalid fileIds"));
        Hibernate.initialize(files);

        WorkType type = workTypeRepository.findById(request.getWorkTypeId()).orElseThrow(
                () -> new IllegalArgumentException("Invalid typeId")
        );

        User createdUserApplication = userRepository.findById(request.getCreatedByUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid createdUserId"));

        User zoneOwnerUserApplication = userRepository.findById(request.getZoneOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid zoneOwnerId"));

        User responsibleUserApplication = userRepository.findById(request.getResponsiblePersonId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid responsibleUserId"));

        Organization organization = organizationRepository.findById(request.getOrganizationId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid organizationId"));

        Application application = Application.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .startDate(request.getStartDate())
                .dueDate(request.getEndDate())
                .isCompleted(false)
                .workType(type)
                .isSafetyBriefingCompleted(false)
                .isFireSafetyTrainingCompleted(false)
                .isElectricalSafetyTrainingCompleted(false)
                .zoneOwnerApplication(zoneOwnerUserApplication)
                .zoneOwnerApproval(false)
                .responsiblePersonApplication(responsibleUserApplication)
                .createdByUserApplication(createdUserApplication)
                .securityApproval(false)
                .organizationByApplication(organization)
                .files(files)
                .build();

        Application newApplication = applicationRepository.save(application)
                .orElseThrow(() -> new RuntimeException("Application not created"));

        return applicationMapper.map(newApplication);
    }

    @Override
    @Transactional
    public List<ApplicationDto> findAllApplications(Long id) {
        List<Application> applications = applicationRepository.findAllApplications(id)
                .orElseThrow(() -> new IllegalArgumentException("Applications not found for user id: " + id));

        List<ApplicationDto> applicationDtos = new ArrayList<>();
        for (Application application : applications) {
            applicationDtos.add(applicationMapper.map(application));
        }

        return applicationDtos;
    }


}
