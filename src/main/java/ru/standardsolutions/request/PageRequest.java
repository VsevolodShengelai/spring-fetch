package ru.standardsolutions.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Параметры пагинации.
 */
@Getter
@ToString
@EqualsAndHashCode
@Schema(description = "Параметры пагинации")
public class PageRequest {

    @NotNull
    @Min(value = 1, message = "Номер страницы не может быть меньше {value}")
    @Max(value = Integer.MAX_VALUE, message = "Номер страницы не может быть больше {value}")
    @Schema(description = "Номер страницы", defaultValue = "1")
    private final Integer number;

    @NotNull
    @Min(value = 1, message = "Размер страницы не может быть меньше {value}")
    @Max(value = 100, message = "Размер страницы не может быть больше {value}")
    @Schema(description = "Размер страницы", defaultValue = "50")
    private final Integer size;

    @JsonCreator
    public PageRequest(@JsonProperty("number") Integer number,
                       @JsonProperty("size") Integer size) {
        this.number = number;
        this.size = size;
    }
}
