package ru.minusd.security.domain.dto;
import lombok.Data;
import ru.minusd.security.domain.model.Role;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private Role role;
    private OrganizationDto organization;
}
