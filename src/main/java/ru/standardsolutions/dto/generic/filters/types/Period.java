package ru.standardsolutions.dto.generic.filters.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;

/**
 * Класс, представляющий период времени с началом и концом.
 */
@Getter
@Schema(description = "Период времени")
public class Period {

    @Schema(description = "Начало периода", requiredMode = NOT_REQUIRED)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime from;

    @Schema(description = "Окончание периода", requiredMode = NOT_REQUIRED)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime to;

    @JsonCreator
    public Period(@JsonProperty("from") LocalDateTime from,
                  @JsonProperty("to") LocalDateTime to) {
        this.from = from;
        this.to = to;
    }
}