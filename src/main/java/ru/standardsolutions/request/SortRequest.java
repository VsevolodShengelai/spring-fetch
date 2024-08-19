package ru.standardsolutions.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.standardsolutions.SortDirection;

@Getter
@ToString
@EqualsAndHashCode
public class SortRequest {

    @NotNull
    private final String field;

    @NotNull
    private final SortDirection direction;

    @JsonCreator
    public SortRequest(@JsonProperty("field") String field,
                       @JsonProperty("direction") SortDirection direction) {
        this.field = field;
        this.direction = direction;
    }
}
