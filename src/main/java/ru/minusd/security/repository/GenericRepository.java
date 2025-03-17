package ru.minusd.security.repository;

import ru.minusd.security.domain.model.FileInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, PK extends Serializable>{
    Optional<T> save(T entity);
    Optional<T> findById(PK primaryKey);
    List<T> findAll();
    void deleteById(PK primaryKey);
}

