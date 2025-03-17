package ru.minusd.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.minusd.security.domain.dto.request.AddFilesInTaskRequest;
import ru.minusd.security.domain.dto.request.TaskCreateRequest;
import ru.minusd.security.domain.dto.TaskDto;
import ru.minusd.security.domain.model.FileInfo;
import ru.minusd.security.domain.model.Task;
import ru.minusd.security.domain.model.User;
import ru.minusd.security.mapper.impl.TaskMapper;
import ru.minusd.security.repository.FileRepository;
import ru.minusd.security.repository.TaskRepository;
import ru.minusd.security.repository.UserRepository;
import ru.minusd.security.service.TaskService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskDto save(TaskCreateRequest request) {

        Set<FileInfo> files = fileRepository.findByFileIds(request.getFileIds()).orElseThrow(
                () -> new IllegalArgumentException("Invalid fileIds")
        );
        Set<User> users = userRepository.findAllByUserIds(request.getUserIds()).orElseThrow(
                () -> new IllegalArgumentException("Invalid userIds")
        );

        var task = Task.builder().title(request.getTitle()).description(request.getDescription())
                .createdAt(LocalDate.now()).updatedAt(LocalDate.now()).startDate(request.getStartDate())
                .dueDate(request.getEndDate()).users(users).files(files).build();
        Task newTask = taskRepository.save(task).orElseThrow(
                () -> new RuntimeException("Task Not Found")
        );

        return taskMapper.map(newTask);
    }

    @Override
    //@Cacheable(value = "tasks",key = "#id")
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Task not found: " + id)
        );
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void addFilesInTask(AddFilesInTaskRequest request){
        Set<FileInfo> files = new HashSet<>();
        request.getFilesIds().forEach(fileId->{
            files.add(fileRepository.findById(fileId).orElseThrow(
                    () -> new IllegalArgumentException("File not found: " + fileId)
            ));
        });
        taskRepository.addFiles(request.getTaskId(), files);
    }
}
