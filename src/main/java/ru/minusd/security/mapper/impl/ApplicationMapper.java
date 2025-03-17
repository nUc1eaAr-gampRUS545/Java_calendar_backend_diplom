package ru.minusd.security.mapper.impl;

import ru.minusd.security.domain.dto.ApplicationDto;
import ru.minusd.security.domain.dto.OrganizationDto;
import ru.minusd.security.domain.dto.UserDto;
import ru.minusd.security.domain.model.Application;
import ru.minusd.security.domain.model.Organization;
import ru.minusd.security.mapper.GenericMap;

public class ApplicationMapper implements GenericMap<Application, ApplicationDto> {
    private final UserMapper userMapper = new UserMapper();
    private final OrganizationMapper organizationMapper = new OrganizationMapper();

    @Override
    public ApplicationDto map(Application object) {
        if(object == null) return null;
        OrganizationDto organization = organizationMapper.map(object.getOrganization());
        UserDto createdUser = userMapper.map(object.getCreatedByUser());
        UserDto responsibleUser = userMapper.map(object.getResponsiblePerson());
        UserDto zoneOwner = userMapper.map(object.getZoneOwner());

        ApplicationDto applicationDto = new ApplicationDto();

        applicationDto.setId(object.getId());
        applicationDto.setFirstname(object.getFirstname());
        applicationDto.setLastname(object.getLastname());
        applicationDto.setEmail(object.getEmail());
        applicationDto.setPhoneNumber(object.getPhoneNumber());
        applicationDto.setStartDate(object.getStartDate());
        applicationDto.setOrganization(organization);
        applicationDto.setCreatedByUser(responsibleUser);
        applicationDto.setZoneOwner(zoneOwner);
        applicationDto.setCreatedByUser(createdUser);
        applicationDto.setIsElectricalSafetyTrainingCompleted(object.getIsElectricalSafetyTrainingCompleted());
        applicationDto.setIsSafetyBriefingCompleted(object.getIsSafetyBriefingCompleted());
        applicationDto.setIsFireSafetyTrainingCompleted(object.getIsFireSafetyTrainingCompleted());
        applicationDto.setIsCompleted(object.isCompleted());
        applicationDto.setSecurityApproval(object.isSecurityApproval());
        applicationDto.setZoneOwnerApproval(object.isZoneOwnerApproval());
        applicationDto.setFiles(object.getFiles());

        return applicationDto;

    }
}
