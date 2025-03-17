package ru.minusd.security.repository;

import org.springframework.stereotype.Repository;
import ru.minusd.security.domain.model.Organization;
import ru.minusd.security.domain.model.Task;
import ru.minusd.security.domain.model.User;

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
}