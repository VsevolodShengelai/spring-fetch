package ru.standardsolutions.dto.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Направление сортировки.
 */
@Schema(description = "Направление сортировки")
public enum SortDirection {

    @Schema(description = "По возрастанию")
    ASC("ASC"),

    @Schema(description = "По убыванию")
    DESC("DESC");

    private final String value;

    SortDirection(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static SortDirection fromValue(String value) {
        for (SortDirection direction : values()) {
            if (direction.value.equalsIgnoreCase(value)) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Неизвестное направление сортировки: " + value);
    }
}