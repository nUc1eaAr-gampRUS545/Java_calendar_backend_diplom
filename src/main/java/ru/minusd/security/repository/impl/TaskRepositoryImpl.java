package ru.minusd.security.repository.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.minusd.security.domain.model.FileInfo;
import ru.minusd.security.domain.model.Task;
import ru.minusd.security.repository.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public Optional<Task> save(Task task) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(task);
            transaction.commit();

        }
        return Optional.ofNullable(task);
    }

    @Override
    public Optional<Task> findById(Long id) {
        try (Session session = getSession()) {
            return Optional.ofNullable(session.get(Task.class,id));
        }

    }

    @Override
    public List<Task> findAll() {
        try (Session session = getSession()) {
            List tasks = session.createQuery("from Task").list();
            return tasks;
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            Optional<Task> task = findById(id);
            if(task.isPresent()) {
                session.remove(task);
                transaction.commit();
            }

        }
    }
    public void addFiles(Long taskId, Set<FileInfo> files) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            Task task = findById(taskId).get();
            task.setFiles(files);
            session.merge(task);
            transaction.commit();
        }
    };
}
