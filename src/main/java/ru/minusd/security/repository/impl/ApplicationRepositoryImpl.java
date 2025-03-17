package ru.minusd.security.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.minusd.security.domain.model.Application;
import ru.minusd.security.repository.ApplicationRepository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class ApplicationRepositoryImpl implements ApplicationRepository {

    private final SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public Optional<Application> save(Application application) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(application);
            transaction.commit();

        }
        return Optional.ofNullable(application);
    }


    @Override
    public Optional<Application> findById(Long id) {
        try (Session session = getSession()) {
            return Optional.ofNullable(session.find(Application.class,id));
        }

    }

    @Override
    public List<Application> findAll() {
        return List.of();
    }



    @Override
    public void deleteById(Long id) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            Optional<Application> application = findById(id);
            if (application.isPresent()) {
                session.remove(application);
                transaction.commit();
            }
        }
    }
}
