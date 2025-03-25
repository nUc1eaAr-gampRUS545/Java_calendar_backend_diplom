package ru.minusd.security.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.minusd.security.domain.entity.WorkType;
import ru.minusd.security.repository.WorkTypeRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WorkTypeRepositoryImpl implements WorkTypeRepository {

    private final SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public Optional<WorkType> save(WorkType entity) {
        return Optional.empty();
    }

    @Override
    public Optional<WorkType> findById(Long primaryKey) {
        try(Session session = getSession()){
            WorkType type = session.find(WorkType.class,primaryKey);
            return Optional.ofNullable(type);
        }
    }

    @Override
    public Optional<List<WorkType>> findAll() {
        try(Session session = getSession()){
            List<WorkType> types = session.createQuery("FROM WorkType",WorkType.class).list();
            return Optional.ofNullable(types);
        }
    }

    @Override
    public void deleteById(Long primaryKey) {

    }
}
