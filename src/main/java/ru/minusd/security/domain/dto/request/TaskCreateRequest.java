package ru.minusd.security.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на создание задачи")
public class TaskCreateRequest {

    @Schema(description = "id создателя", example = "7")
    private Long createdBy;

    @Schema(description = "Название задачи", example = "Замена катриджа")
    @Size(min = 5, max = 100, message = "Название задачи должно содержать от 5 до 100 символов")
    @NotBlank(message = "Название задачи пользователя не может быть пустыми")
    private String title;

    @Schema(description = "Описание задачи", example = "Замена катриджа в копировальной комнате на 2 этаже")
    @Size(min = 5, max = 255, message = "Описание задачи пользователя должно содержать от 5 до 255 символов")
    @NotBlank(message = "Описание задачи пользователя не может быть пустыми")
    private String description;

    @Schema(description = "Дата начала", example = "2025-03-25T21:00:06.870")
    @FutureOrPresent(message = "Дата начала должна быть сегодня или позже")
    @NotNull(message = "Дата начала не может быть пустой")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime startDate;

    @Schema(description = "Дата окончания", example = "2025-03-26T21:00:06.870")
    @FutureOrPresent(message = "Дата окончания должна быть сегодня или позже")
    @NotNull(message = "Дата окончания не может быть пустой")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime endDate;

    @Schema(description = "id места", example = "2")
    private Long placeId;

    private Set<Long> userIds;

    private Set<Long> fileIds;
}