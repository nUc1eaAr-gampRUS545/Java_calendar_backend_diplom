package ru.minusd.security.mapper.impl;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.minusd.security.domain.dto.ApplicationDto;
import ru.minusd.security.domain.dto.OrganizationDto;
import ru.minusd.security.domain.dto.UserDto;
import ru.minusd.security.domain.entity.Application;
import ru.minusd.security.domain.entity.WorkType;
import ru.minusd.security.mapper.GenericMap;
@Component
@Data
public class ApplicationMapper implements GenericMap<Application, ApplicationDto> {

    private final UserMapper userMapper;
    private final OrganizationMapper organizationMapper;

    @Override
    public ApplicationDto map(Application object) {
        if(object == null) return null;
        OrganizationDto organization = organizationMapper.map(object.getOrganizationByApplication());
        UserDto createdUser = userMapper.map(object.getCreatedByUserApplication());
        UserDto responsibleUser = userMapper.map(object.getResponsiblePersonApplication());
        UserDto zoneOwner = userMapper.map(object.getZoneOwnerApplication());

        ApplicationDto applicationDto = new ApplicationDto();

        applicationDto.setId(object.getId());
        applicationDto.setFirstname(object.getFirstname());
        applicationDto.setLastname(object.getLastname());
        applicationDto.setEmail(object.getEmail());
        applicationDto.setType(object.getWorkType());
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
