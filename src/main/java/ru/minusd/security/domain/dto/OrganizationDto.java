package ru.minusd.security.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrganizationDto implements Serializable {
    private Long id;
    private String title;
    private String description;
    private OrganizationDto organization;
}
