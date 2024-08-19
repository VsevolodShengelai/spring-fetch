package ru.standardsolutions.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import ru.standardsolutions.FetchSpecification;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class FetchRequest {

    private final List<FilterRequest> filters;

    private final List<SortRequest> sort;

    private final ru.standardsolutions.request.PageRequest page;

    @JsonCreator
    public FetchRequest(@JsonProperty("filters") List<FilterRequest> filters,
                        @JsonProperty("sort") List<SortRequest> sort,
                        @JsonProperty("page") ru.standardsolutions.request.PageRequest page) {
        this.filters = filters;
        this.sort = sort;
        this.page = page;
    }

    public Pageable toPageable() {
        Order id1 = Order.desc("id");
        org.springframework.data.domain.Sort id = org.springframework.data.domain.Sort.by(id1);
        return PageRequest.of(page.getNumber(), page.getSize(), id);
    }

    public <T> Specification<T> toSpecification() {
        return new FetchSpecification<>(this);
    }
}