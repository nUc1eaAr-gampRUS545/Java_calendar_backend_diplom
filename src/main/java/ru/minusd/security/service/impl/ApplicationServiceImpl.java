package ru.minusd.security.service.impl;

import org.springframework.stereotype.Service;
import ru.minusd.security.domain.dto.ApplicationDto;
import ru.minusd.security.domain.dto.request.ApplicationCreateRequest;
import ru.minusd.security.domain.model.*;
import ru.minusd.security.mapper.impl.ApplicationMapper;
import ru.minusd.security.repository.ApplicationRepository;
import ru.minusd.security.repository.FileRepository;
import ru.minusd.security.repository.OrganizationRepository;
import ru.minusd.security.repository.UserRepository;
import ru.minusd.security.service.ApplicationService;

import java.time.LocalDate;
import java.util.Set;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final  FileRepository fileRepository;
    private final  UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final ApplicationMapper applicationMapper = new ApplicationMapper();

    public ApplicationDto createApplication(ApplicationCreateRequest request) {

        Set<FileInfo> files = fileRepository.findByFileIds(request.getFiles()).orElseThrow(
                () -> new IllegalArgumentException("Invalid fileIds")
        );

        User createdUserApplication = userRepository.findById(request.getCreatedByUser()).orElseThrow(
                () -> new IllegalArgumentException("Invalid createdUserId")
        );
        User zoneOwnerUserApplication = userRepository.findById(request.getZoneOwner()).orElseThrow(
                () -> new IllegalArgumentException("Invalid createdUserId")
        );

        User responsibleUserApplication = userRepository.findById(request.getResponsiblePerson()).orElseThrow(
                () -> new IllegalArgumentException("Invalid responsibleUserId")
        );
        Organization organization = organizationRepository.findById(request.getOrganization()).orElseThrow(
                () -> new IllegalArgumentException("Invalid organizationId")
        );
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
                .isSafetyBriefingCompleted(false)
                .isFireSafetyTrainingCompleted(false)
                .isElectricalSafetyTrainingCompleted(false)
                .zoneOwner(zoneOwnerUserApplication)
                .zoneOwnerApproval(false)
                .responsiblePerson(responsibleUserApplication)
                .createdByUser(createdUserApplication)
                .securityApproval(false)
                .organization(organization)
                .files(files)
                .build();

        Application newApplication = applicationRepository.save(application).orElseThrow(
                () -> new RuntimeException("Application no created")
        );

        return applicationMapper.map(newApplication);



    }

}
