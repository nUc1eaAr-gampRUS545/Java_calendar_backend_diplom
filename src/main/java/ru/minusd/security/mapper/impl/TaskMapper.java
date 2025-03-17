package ru.minusd.security.mapper.impl;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.minusd.security.domain.dto.TaskDto;
import ru.minusd.security.domain.dto.UserDto;
import ru.minusd.security.domain.model.Task;
import ru.minusd.security.mapper.GenericMap;

import java.util.HashSet;
import java.util.Set;

@Component
@Data
public class TaskMapper implements GenericMap<Task, TaskDto> {
    private UserMapper userMapper = new UserMapper();
    @Override
    public TaskDto map(Task task) {
        if (task==null) {
            return null;
        }

        Set<UserDto> users = new HashSet<>();
        task.getUsers().forEach(user -> users.add(userMapper.map(user)));

        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setDueDate(task.getDueDate());
        taskDto.setStartDate(task.getStartDate());
        taskDto.setFiles(task.getFiles());
        taskDto.setPlace(task.getPlace());
        taskDto.setUsers(users);
        return taskDto;
    }

}
