package ru.minusd.security.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.minusd.security.domain.model.Organization;
import ru.minusd.security.repository.OrganizationRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrganizationRepositoryImpl implements OrganizationRepository {

    private final SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public Optional<Organization> save(Organization entity) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();

        }
        return Optional.ofNullable(entity);
    }

    @Override
    public Optional<Organization> findById(Long primaryKey) {
        try (Session session = getSession()) {
            return Optional.ofNullable(session.get(Organization.class,primaryKey));
        }
    }

    @Override
    public List<Organization> findAll() {
        try (Session session = getSession()) {
            return (List<Organization>) session.createQuery("from Organization").list();
        }
    }

    @Override
    public void deleteById(Long primaryKey) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            Optional<Organization> organization = findById(primaryKey);
            if (organization.isPresent()) {
                session.remove(organization);
                transaction.commit();
            }
        }

    }
}
