package ru.minusd.security.domain.dto;

import lombok.Data;
import ru.minusd.security.domain.model.FileInfo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
@Data
public class ApplicationDto implements Serializable {
    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String phoneNumber;

    private LocalDate startDate;

    private LocalDate dueDate;

    private Set<FileInfo> files;

    private OrganizationDto organization;

    private UserDto createdByUser;

    private UserDto responsiblePerson;

    private UserDto zoneOwner;

    private Boolean zoneOwnerApproval;

    private Boolean securityApproval;

    private Boolean isCompleted;

    private Boolean isSafetyBriefingCompleted;

    private Boolean isElectricalSafetyTrainingCompleted;

    private Boolean isFireSafetyTrainingCompleted;

}




