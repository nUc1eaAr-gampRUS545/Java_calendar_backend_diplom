package ru.minusd.security.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.minusd.security.domain.entity.Application;
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
    public Optional<List<Application>> findAllApplications(Long id) {
        try (Session session = getSession()) {
            String hql = "SELECT DISTINCT a FROM Application a " +
                    "LEFT JOIN FETCH a.organizationByApplication " +
                    "LEFT JOIN FETCH a.createdByUserApplication " +
                    "LEFT JOIN FETCH a.responsiblePersonApplication " +
                    "LEFT JOIN FETCH a.zoneOwnerApplication " +
                    "LEFT JOIN FETCH a.files " +
                    "LEFT JOIN FETCH a.workType " +
                    "WHERE a.createdByUserApplication.id = :id " +
                    "OR a.responsiblePersonApplication.id = :id " +
                    "OR a.zoneOwnerApplication.id = :id";

            return Optional.ofNullable(session.createQuery(hql,Application.class).setParameter("id",id).list());
        }
    }

    @Override
    public Optional<List<Application>> findAll() {
        return null;
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
