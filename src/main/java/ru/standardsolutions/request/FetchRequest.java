package ru.standardsolutions.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.standardsolutions.FetchSpecification;
import ru.standardsolutions.SortDirection;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class FetchRequest {

    private final List<Filter> filters;

    private final List<Sort> sort;

    private final Page page;

    @JsonCreator
    public FetchRequest(@JsonProperty("filters") List<Filter> filters,
                        @JsonProperty("sort") List<Sort> sort,
                        @JsonProperty("page") Page page) {
        this.filters = filters;
        this.sort = sort;
        this.page = page;
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    public static class Sort {

        @NotNull
        private final String field;

        @NotNull
        private final SortDirection direction;

        @JsonCreator
        public Sort(@JsonProperty("field") String field,
                    @JsonProperty("direction") SortDirection direction) {
            this.field = field;
            this.direction = direction;
        }
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    public static class Page {

        @NotNull
        private final Integer number;

        @NotNull
        private final Integer size;

        @JsonCreator
        public Page(@JsonProperty("number") Integer number,
                    @JsonProperty("size") Integer size) {
            this.number = number;
            this.size = size;
        }
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    public static class Filter {

        private final String field;

        private final Object value;

        private final String operator;

        private final List<Filter> filters;

        @JsonCreator
        public Filter(@JsonProperty("field") String field,
                      @JsonProperty("value") Object value,
                      @JsonProperty("operator") String operator,
                      @JsonProperty("filters") List<Filter> filters) {
            this.field = field;
            this.value = value;
            this.operator = operator;
            this.filters = filters;
        }
    }

    public Pageable toPageable() {
        return PageRequest.of(page.getNumber(), page.getSize());
    }

    public <T> Specification<T> toSpecification() {
        return new FetchSpecification<>(this);
    }
}