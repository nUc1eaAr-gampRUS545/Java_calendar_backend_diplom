package ru.minusd.security.repository;

import ru.minusd.security.domain.entity.Organization;
import ru.minusd.security.domain.entity.Task;
import ru.minusd.security.domain.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends GenericRepository<User, Long> {
    Optional<User> findByUsername(String username);
    void setOrganization(User user, Organization organization);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void deleteById(Long id);
    Optional<Set<User>> findAllByUserIds(Set<Long> userIds);
    List<Task> findUsersTasks(Long id);
    Optional<List<User>> findAll();
    Optional<User> update(User user);
}