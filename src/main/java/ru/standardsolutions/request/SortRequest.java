package ru.standardsolutions.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Параметры сортировки.
 */
@Getter
@ToString
@EqualsAndHashCode
@Schema(description = "Параметры сортировки")
public class SortRequest {

    @NotNull
    @Size(max = 255, message = "Длина строки должна быть не более {max} символов")
    @Schema(description = "Поле сортировки")
    private final String field;

    @NotNull
    @Size(max = 255, message = "Длина строки должна быть не более {max} символов")
    @Schema(description = "Направление сортировки")
    private final String direction;

    @JsonCreator
    public SortRequest(@JsonProperty("field") String field,
                       @JsonProperty("direction") String direction) {
        this.field = field;
        this.direction = direction;
    }
}