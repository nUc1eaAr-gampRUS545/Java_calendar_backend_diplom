package ru.minusd.security.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;
import ru.minusd.security.domain.entity.Organization;
import ru.minusd.security.domain.entity.Task;
import ru.minusd.security.domain.entity.User;
import ru.minusd.security.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public Optional<User> save(User user) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();

        }
        return Optional.ofNullable(user);
    }
    @Override
    public Optional<User> update(User user) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();

        }
        return Optional.ofNullable(user);
    }
    @Override
    public void setOrganization(User user, Organization organization) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            user.setOrganizationByUser(organization);
            session.merge(organization);
            transaction.commit();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (Session session = getSession()) {
            String hql = "FROM User u WHERE u.username = :username";
            Query<User> query = session.createQuery(hql, User.class).setParameter("username", username);
            return query.uniqueResultOptional();
        }

    }

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = getSession()) {
            return Optional.ofNullable(session.find(User.class,id));
        }

    }
    @Override
    public List<Task> findUsersTasks(Long id) {
        try (Session session = getSession()) {
            return session.find(User.class,id).getTasks().stream().toList();
        }

    }

    @Override
    public Optional<List<User>> findAll() {
        try (Session session = getSession()){
            return Optional.ofNullable(session.createQuery("from User").list());
        }
    }
    @Override
    public Optional<Set<User>> findAllByUserIds(Set<Long> userIds) {

        Set<User> users = new HashSet<>();
        userIds.forEach(userId -> users.add(getById(userId)));
        return Optional.of(users);
    }

    @Override
    public boolean existsByUsername(String username) {
        try (Session session = getSession()) {
            String hql = "SELECT count(u) FROM User u WHERE u.username = :username";
            Query<Long> query = session.createQuery(hql,Long.class).setParameter("username", username);
            return query.uniqueResult() > 0;
        }

    }

    @Override
    public boolean existsByEmail(String email) {
        try (Session session = getSession()) {
            String hql = "SELECT count(u) FROM User u WHERE u.email = :email";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("email", email);
            return query.uniqueResult() > 0;
        }

    }

    @Override
    public void deleteById(Long id) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            Optional<User> user = findById(id);
            if (user.isPresent()) {
                session.remove(user);
                transaction.commit();
            }
        }
    }
    private User getById(Long id) {
        try (Session session = getSession()) {
            return session.find(User.class,id);
        }

    }
}
