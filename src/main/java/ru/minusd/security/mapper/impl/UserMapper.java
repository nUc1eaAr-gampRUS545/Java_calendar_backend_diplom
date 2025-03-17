package ru.minusd.security.mapper.impl;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.minusd.security.domain.dto.OrganizationDto;
import ru.minusd.security.domain.dto.UserDto;
import ru.minusd.security.domain.model.User;
import ru.minusd.security.mapper.GenericMap;

@Component
@Data
public class UserMapper implements GenericMap<User, UserDto> {
    private OrganizationMapper organizationMapper = new OrganizationMapper();
    @Override
    public UserDto map(User user) {

        if(user == null){
            return null;
        }

        OrganizationDto organizationDto = organizationMapper.map(user.getOrganization());
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setOrganization(organizationDto);
        return userDto;
    }

}
