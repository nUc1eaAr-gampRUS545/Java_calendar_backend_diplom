package ru.minusd.security.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.minusd.security.domain.model.FileInfo;
import ru.minusd.security.repository.FileRepository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {

    private static final String CREATE_FILE = "INSERT INTO files_info(file_name, file_size, file_key, upload_date) VALUES (?, ?, ?, ?)";
    private static final String DELETE_FILE_BY_ID = "DELETE FROM files_info WHERE id = ?";
    private static final String FIND_FILE_BY_ID = "SELECT id, file_name, file_size, file_key, upload_date FROM files_info WHERE id = ?";
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<FileInfo> save(final FileInfo file) {
        LocalDate uploadDate = LocalDate.now();
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(x -> {
            PreparedStatement preparedStatement = x.prepareStatement(CREATE_FILE,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,file.getName());
            preparedStatement.setLong(2,file.getSize());
            preparedStatement.setString(3,file.getKey());
            preparedStatement.setDate(4,Date.valueOf(uploadDate));
            return preparedStatement;
        },keyHolder);

        Number key = (Number) keyHolder.getKeys().get("id");
        return Optional.ofNullable(file.toBuilder()
                .id(key.longValue())
                .uploadDate(uploadDate)
                .build());
    }



    @Override
    public Optional<FileInfo> findById(Long fileId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_FILE_BY_ID,rowMapper(),fileId));
    }

    @Override
    public List<FileInfo> findAll() {
        return List.of();
    }


    @Override
    public void deleteById(Long fileId) {
        jdbcTemplate.update(DELETE_FILE_BY_ID, fileId);
    }

    private RowMapper<FileInfo> rowMapper() {
        return (rs, rowNum) -> FileInfo.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("file_name"))
                .size(rs.getLong("file_size"))
                .key(rs.getString("file_key"))
                .uploadDate(rs.getObject("upload_date", LocalDate.class))
                .build();
    }

    @Override
    public Optional<Set<FileInfo>>  findByFileIds(Set<Long> fileIds) {
        Set<FileInfo> files = new HashSet<>();
        fileIds.forEach(fileId -> {
            files.addAll(jdbcTemplate.query(FIND_FILE_BY_ID,rowMapper(),fileId));
        });
        return Optional.of(files);
    }
}