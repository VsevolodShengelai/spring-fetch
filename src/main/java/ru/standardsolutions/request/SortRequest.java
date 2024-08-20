package ru.standardsolutions.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class SortRequest {

    @NotNull
    private final String field;

    @NotNull
    private final String direction;

    @JsonCreator
    public SortRequest(@JsonProperty("field") String field,
                       @JsonProperty("direction") String direction) {
        this.field = field;
        this.direction = direction;
    }
}