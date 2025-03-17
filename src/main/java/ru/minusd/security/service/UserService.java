package ru.minusd.security.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.minusd.security.domain.model.Organization;
import ru.minusd.security.domain.model.Task;
import ru.minusd.security.domain.model.User;

import java.util.List;

public interface UserService {
    User save(User user);
    User create(User user);
    User getByUsername(String username);
    UserDetailsService userDetailsService();
    List<Task> findUserTasksById(Long id);
    User findById(Long id);
    void setOrganization(User user, Organization organization);
}
