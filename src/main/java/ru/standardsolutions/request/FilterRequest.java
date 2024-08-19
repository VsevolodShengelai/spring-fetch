package ru.standardsolutions.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class FilterRequest {

    private final String field;

    private final Object value;

    private final String operator;

    private final List<FilterRequest> filters;

    @JsonCreator
    public FilterRequest(@JsonProperty("field") String field,
                         @JsonProperty("value") Object value,
                         @JsonProperty("operator") String operator,
                         @JsonProperty("filters") List<FilterRequest> filters) {
        this.field = field;
        this.value = value;
        this.operator = operator;
        this.filters = filters;
    }
}
