package ru.minusd.security.domain.dto;

import lombok.Data;
import ru.minusd.security.domain.model.FileInfo;
import ru.minusd.security.domain.model.Place;
import ru.minusd.security.domain.model.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class TaskDto implements Serializable {

    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Place place;
    private Set<FileInfo> files = new HashSet<>();
    private Set<UserDto> users  = new HashSet<>();
}
