package ru.minusd.security.repository;

import ru.minusd.security.domain.entity.Application;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends GenericRepository<Application, Long> {
    Optional<List<Application>> findAllApplications(Long id);
}
