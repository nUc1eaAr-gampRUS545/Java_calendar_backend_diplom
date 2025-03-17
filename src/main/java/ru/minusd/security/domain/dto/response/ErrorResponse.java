package ru.minusd.security.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ при ошибке запроса")
public class ErrorResponse {

    @Schema(description = "Флаг ошибки", example = "false")
    private boolean success;

    @Schema(description = "Код ошибки", example = "400")
    private int status;

    @Schema(description = "Описание ошибки", example = "Некорректные данные")
    private String message;

    @Schema(description = "Детали ошибки (если есть)")
    private String details;
}
