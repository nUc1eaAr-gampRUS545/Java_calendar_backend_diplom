package ru.minusd.security.domain.dto;

import lombok.Data;
import ru.minusd.security.domain.entity.FileInfo;
import ru.minusd.security.domain.entity.Place;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class TaskDto implements Serializable {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private Place place;
    private String importance;
    private Set<FileInfo> files = new HashSet<>();
    private Set<UserDto> users  = new HashSet<>();
}
