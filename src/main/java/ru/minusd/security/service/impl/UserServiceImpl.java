package ru.minusd.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.minusd.security.domain.model.Organization;
import ru.minusd.security.domain.model.Role;
import ru.minusd.security.domain.model.Task;
import ru.minusd.security.domain.model.User;
import ru.minusd.security.repository.UserRepository;
import ru.minusd.security.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    //@CacheEvict(value = "users", allEntries = true) // Очистка кеша при сохранении
    public User save(User user) {
        return repository.save(user).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }


    @Override
   // @Cacheable(value = "users", key = "#user.username", unless = "#result == null") // Кеширование при создании
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

    @Override
    //@Cacheable(value = "users", key = "#username", unless = "#result == null") // Кеширование по username
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с username = " + username + " не найден!"));
    }

    @Override
    //@Cacheable(value = "users", key = "#id", unless = "#result == null") // Кеширование по id
    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с id = " + id + " не найден!"));
    }

    @Override
    public List<Task> findUserTasksById(Long id) {
        return repository.findUsersTasks(id);
    }

    @Override
   // @CacheEvict(value = "users", key = "#user.username") // Очистка кеша при изменении организации
    public void setOrganization(User user, Organization organization) {
        repository.setOrganization(user, organization);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }


    //@Cacheable(value = "users", key = "#root.methodName + '-' + #root.args[0]") // Кеширование текущего пользователя
    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    // @CacheEvict(value = "users", key = "#root.target.getCurrentUser().username") // Очистка кеша при изменении роли
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }
}