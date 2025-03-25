package ru.minusd.security.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на создание заявки на пропуск")

public class ApplicationCreateRequest {

    @Schema(description = "Имя пользователя", example = "Jon")
    @Size(min = 3, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String firstname;

    @Schema(description = "Фамилия пользователя", example = "Jon")
    @Size(min = 3, max = 50, message = "Фамилия пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Фамилия пользователя не может быть пустыми")
    private String lastname;

    @Schema(description = "Адрес электронной почты", example = "jondoe@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Schema(description = "Телефон пользователя", example = "+7(999)999-99-99")
    @Size(min = 11, max = 16, message = "Телефон пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Телефон пользователя не может быть пустыми")
    private String phoneNumber;

    @Schema(description = "Дата начала", example = "2025-03-25")
    @FutureOrPresent(message = "Дата окончания не может быть пустыми")
    @NotNull(message = "Дата начала не может быть пустыми")
    private LocalDate startDate;

    @Schema(description = "Дата окончания", example = "2025-03-26")
    @FutureOrPresent(message = "Дата окончания не может быть пустыми")
    @NotNull(message = "Дата окончания не может быть пустыми")
    private LocalDate endDate;

    @Schema(description = "ID организации", example = "1")
    @Min(value = 1, message = "ID организации должно быть положительным числом")
    private Long organizationId;

    @Schema(description = "ID создателя задачи", example = "1")
    @Min(value = 1, message = "ID создателя заявки должно быть положительным числом")
    private Long createdByUserId;

    @Schema(description = "ID создателя типа задачи", example = "1")
    @Min(value = 1, message = "ID типа задачи должно быть положительным числом")
    private Long workTypeId;

    @Schema(description = "ID ответственного лица", example = "1")
    @Min(value = 1, message = "ID ответственного лица должно быть положительным числом")
    private Long responsiblePersonId;

    @Schema(description = "ID ответственного за зону", example = "1")
    @Min(value = 1, message = "ID ответственного лица должно быть положительным числом")
    private Long zoneOwnerId;

    @Schema(description = "ID's файлов", example = "[1,2,3]")
    private Set<Long> fileIds;


}
