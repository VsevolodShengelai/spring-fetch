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
public class PageRequest {

    @NotNull
    private final Integer number;

    @NotNull
    private final Integer size;

    @JsonCreator
    public PageRequest(@JsonProperty("number") Integer number,
                       @JsonProperty("size") Integer size) {
        this.number = number;
        this.size = size;
    }
}
