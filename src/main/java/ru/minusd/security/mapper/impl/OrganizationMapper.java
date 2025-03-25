package ru.minusd.security.mapper.impl;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.minusd.security.domain.dto.OrganizationDto;
import ru.minusd.security.domain.entity.Organization;
import ru.minusd.security.mapper.GenericMap;

@Component
@Data
public class OrganizationMapper implements GenericMap<Organization, OrganizationDto> {
    @Override
    public OrganizationDto map(Organization organization) {

        if (organization==null) {
            return null;
        }

        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setId(organization.getId());
        organizationDto.setTitle(organization.getTitle());
        organizationDto.setDescription(organization.getDescription());

        return organizationDto;
    }

}
