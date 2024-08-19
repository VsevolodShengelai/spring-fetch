package ru.standardsolutions;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import ru.standardsolutions.request.FetchRequest;
import ru.standardsolutions.request.FilterRequest;

import java.util.ArrayList;
import java.util.List;

public class FetchSpecification<T> implements Specification<T> {

    private final FetchRequest fetchRequest;

    public FetchSpecification(FetchRequest fetchRequest) {
        this.fetchRequest = fetchRequest;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return buildPredicates(root, criteriaBuilder, fetchRequest.getFilters(), "AND");
    }

    private Predicate buildPredicates(Root<T> root, CriteriaBuilder criteriaBuilder, List<FilterRequest> filters,
                                      String groupOperator) {
        List<Predicate> predicates = new ArrayList<>();
        for (FilterRequest filter : filters) {
            String operator = filter.getOperator();
            boolean isGroupOperator = "OR".equalsIgnoreCase(operator) || "AND".equalsIgnoreCase(operator);
            if (isGroupOperator) {
                Predicate groupPredicate = buildPredicates(root, criteriaBuilder, filter.getFilters(), filter.getOperator());
                predicates.add(groupPredicate);
            } else {
                predicates.add(createPredicate(filter, root, criteriaBuilder));
            }
        }

        if ("OR".equalsIgnoreCase(groupOperator)) {
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        } else {
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
    }

    private Predicate createPredicate(FilterRequest filter, Root<T> root, CriteriaBuilder criteriaBuilder) {
        Path<Object> path = root.get(filter.getField());
        switch (filter.getOperator()) {
            case ":":
                return criteriaBuilder.equal(path, filter.getValue());
            default:
                throw new IllegalArgumentException("Unsupported operator: " + filter.getOperator());
        }
    }
}
