package ru.minusd.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.minusd.security.domain.dto.request.AddFilesInTaskRequest;
import ru.minusd.security.domain.dto.request.TaskCreateRequest;
import ru.minusd.security.domain.dto.TaskDto;
import ru.minusd.security.domain.dto.request.TaskUpdateRequest;
import ru.minusd.security.domain.entity.*;
import ru.minusd.security.mapper.impl.TaskMapper;
import ru.minusd.security.repository.FileRepository;
import ru.minusd.security.repository.PlaceRepository;
import ru.minusd.security.repository.TaskRepository;
import ru.minusd.security.repository.UserRepository;
import ru.minusd.security.service.TaskService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final PlaceRepository placeRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskDto save(TaskCreateRequest request) {

        Long createdById = Long.parseLong(String.valueOf(request.getCreatedBy()));

        User createdUser = userRepository.findById(createdById).orElseThrow(
                () -> new IllegalArgumentException("Invalid created by " + createdById)
        );

        if (createdUser.getRole()!=Role.ROLE_USER && createdUser.getRole()!=Role.ROLE_SECURITY) {
            Place place = placeRepository.findById(request.getPlaceId()).orElseThrow(
                    () -> new IllegalArgumentException("Invalid placeId"));
            Set<FileInfo> files = fileRepository.findByFileIds(request.getFileIds()).orElseThrow(
                    () -> new IllegalArgumentException("Invalid fileIds")
            );
            Set<User> users = userRepository.findAllByUserIds(request.getUserIds()).orElseThrow(
                    () -> new IllegalArgumentException("Invalid userIds")
            );

            var task = Task.builder().title(request.getTitle()).description(request.getDescription())
                    .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).startDate(request.getStartDate())
                    .dueDate(request.getEndDate()).users(users).createdUserTask(createdUser).place(place).files(files).build();
            Task newTask = taskRepository.save(task).orElseThrow(
                    () -> new RuntimeException("Task Not Found")
            );

            return taskMapper.map(newTask);
        }
        else {
            throw new IllegalArgumentException("У данного пользователя нет прав на создание задач");
        }

    }
    @Override
    @Transactional
    public TaskDto updateTask(TaskUpdateRequest request) {
        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("Задача с ID " + request.getTaskId() + " не найдена"));

        Long createdById = Long.parseLong(String.valueOf(task.getCreatedUserTask().getId()));
        User createdUser = userRepository.findById(createdById)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с ID " + createdById + " не найден"));

        if (createdUser.getRole() != Role.ROLE_USER && createdUser.getRole() != Role.ROLE_SECURITY) {

            task.setTitle(request.getTitle());
            task.setDescription(request.getDescription());
            task.setStartDate(request.getStartDate());
            task.setDueDate(request.getEndDate());
            task.setUpdatedAt(LocalDateTime.now());

            if (request.getPlaceId() != null) {
                Place place = placeRepository.findById(request.getPlaceId())
                        .orElseThrow(() -> new IllegalArgumentException("Неверный placeId"));
                task.setPlace(place);
            }

            if (request.getFileIds() != null && !request.getFileIds().isEmpty()) {
                Set<FileInfo> files = fileRepository.findByFileIds(request.getFileIds())
                        .orElseThrow(() -> new IllegalArgumentException("Неверные fileIds"));
                task.setFiles(files);
            }

            if (request.getUserIds() != null && !request.getUserIds().isEmpty()) {
                Set<User> users = userRepository.findAllByUserIds(request.getUserIds())
                        .orElseThrow(() -> new IllegalArgumentException("Неверные userIds"));
                task.setUsers(users);
            }

            Task updatedTask = taskRepository.save(task).orElseThrow(
                    ()->new IllegalArgumentException("Задача не обновлена")
            );
            return taskMapper.map(updatedTask);
        } else {
            throw new IllegalArgumentException("У данного пользователя нет прав на обновление задач");
        }
    }
    @Override
    //@Cacheable(value = "tasks",key = "#id")
    public TaskDto findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Task not found: " + id)
        );
        return taskMapper.map(task);
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void addFilesInTask(AddFilesInTaskRequest request) {
        Set<FileInfo> files = new HashSet<>();
        request.getFilesIds().forEach(fileId -> {
            files.add(fileRepository.findById(fileId).orElseThrow(
                    () -> new IllegalArgumentException("File not found: " + fileId)
            ));
        });
        taskRepository.addFiles(request.getTaskId(),files);
    }
}
