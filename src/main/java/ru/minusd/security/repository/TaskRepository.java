package ru.minusd.security.repository;

import ru.minusd.security.domain.entity.FileInfo;
import ru.minusd.security.domain.entity.Task;

import java.util.Set;

public interface TaskRepository extends GenericRepository<Task, Long> {
    void addFiles(Long taskId, Set<FileInfo> files);
}
