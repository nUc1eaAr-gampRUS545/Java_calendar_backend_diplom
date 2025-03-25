package ru.minusd.security.repository;

import ru.minusd.security.domain.entity.FileInfo;

import java.util.Optional;
import java.util.Set;

public interface FileRepository extends GenericRepository<FileInfo, Long> {
    Optional<Set<FileInfo>> findByFileIds(Set<Long> fileIds);
}
