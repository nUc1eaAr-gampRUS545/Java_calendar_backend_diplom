package ru.minusd.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.minusd.security.domain.dto.TaskDto;
import ru.minusd.security.domain.dto.UserDto;
import ru.minusd.security.domain.entity.User;

import java.util.List;

public interface UserService {
    User save(User user);
    User create(User user);
    User getByUsername(String username);
    UserDetailsService userDetailsService();
    List<TaskDto> findUserTasksById(Long id);
    User findById(Long id);
    List<UserDto> findAll();
}
