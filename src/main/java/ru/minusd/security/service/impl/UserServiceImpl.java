package ru.minusd.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.minusd.security.domain.dto.TaskDto;
import ru.minusd.security.domain.dto.UserDto;
import ru.minusd.security.domain.entity.Role;
import ru.minusd.security.domain.entity.User;
import ru.minusd.security.mapper.impl.TaskMapper;
import ru.minusd.security.mapper.impl.UserMapper;
import ru.minusd.security.repository.UserRepository;
import ru.minusd.security.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @Override
    //@CacheEvict(value = "users", allEntries = true) // Очистка кеша при сохранении
    public User save(User user) {
        return repository.save(user).orElseThrow(() -> new UsernameNotFoundException("User not found"));
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
    @Transactional
    //@Cacheable(value = "users", key = "#id", unless = "#result == null") // Кеширование по id
    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с id = " + id + " не найден!"));
    }

    @Override
    public List<UserDto> findAll() {
        List<UserDto> usersDto = new ArrayList<>();
        List<User> users = repository.findAll()
                .orElseThrow(() -> new UsernameNotFoundException("Пользователи не найдены!"));
        users.forEach(user -> {
            usersDto.add(userMapper.map(user));
        });
        return usersDto;
    }

    @Override
    public List<TaskDto> findUserTasksById(Long id) {
        List<TaskDto> taskDtos = new ArrayList<>();
        repository.findUsersTasks(id).forEach(task -> {
            TaskDto taskDto = taskMapper.map(task);
            taskDtos.add(taskDto);
        });
        return taskDtos;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }


    //@Cacheable(value = "users", key = "#root.methodName + '-' + #root.args[0]")
    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    // @CacheEvict(value = "users", key = "#root.target.getCurrentUser().username")
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }

}