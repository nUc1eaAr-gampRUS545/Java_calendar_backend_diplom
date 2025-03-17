package ru.minusd.security.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Общий ответ на успешный запрос")
public class SuccessResponse<T> {

    @Schema(description = "Флаг успешного запроса", example = "true")
    private boolean success;

    @Schema(description = "Сообщение об успехе", example = "Операция выполнена успешно")
    private String message;

    @Schema(description = "Данные ответа (если есть)")
    private T data;
}
