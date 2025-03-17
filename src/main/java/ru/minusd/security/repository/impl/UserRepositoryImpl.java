package ru.minusd.security.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;
import ru.minusd.security.domain.model.FileInfo;
import ru.minusd.security.domain.model.Organization;
import ru.minusd.security.domain.model.Task;
import ru.minusd.security.domain.model.User;
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
    public void setOrganization(User user, Organization organization) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            user.setOrganization(organization);
            session.merge(organization);
            transaction.commit();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (Session session = getSession()) {
            String hql = "FROM User u WHERE u.username = :username";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);
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
    public List<User> findAll() {
        return List.of();
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
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("username", username);
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
