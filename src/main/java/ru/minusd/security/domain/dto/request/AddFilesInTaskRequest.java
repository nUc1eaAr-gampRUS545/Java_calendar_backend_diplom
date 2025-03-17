package ru.minusd.security.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на добавление файлов")
public class AddFilesInTaskRequest {
    @Schema(description = "ID задачи", example = "1")
    @Min(value = 1, message = "ID задачи должно быть положительным числом")
    private Long taskId;
    @Schema(description = "ID's файлов", example = "[1,2,3]")
    private Set<Long> filesIds;
}
