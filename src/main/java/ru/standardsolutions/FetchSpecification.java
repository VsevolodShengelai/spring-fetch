package ru.standardsolutions;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import ru.standardsolutions.request.FetchRequest;

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

    private Predicate buildPredicates(Root<T> root, CriteriaBuilder criteriaBuilder, List<FetchRequest.Filter> filters, String operator) {
        List<Predicate> predicates = new ArrayList<>();

        for (FetchRequest.Filter filter : filters) {
            if ("OR".equalsIgnoreCase(filter.getOperator()) || "AND".equalsIgnoreCase(filter.getOperator())) {
                Predicate groupPredicate = buildPredicates(root, criteriaBuilder, filter.getFilters(), filter.getOperator());
                predicates.add(groupPredicate);
            } else {
                predicates.add(createPredicate(filter, root, criteriaBuilder));
            }
        }

        if ("OR".equalsIgnoreCase(operator)) {
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        } else {
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
    }

    private Predicate createPredicate(FetchRequest.Filter filter, Root<T> root, CriteriaBuilder criteriaBuilder) {
        Path<Object> path = root.get(filter.getField());
        switch (filter.getOperator()) {
            case "eq":
                return criteriaBuilder.equal(path, filter.getValue());
            default:
                throw new IllegalArgumentException("Unsupported operator: " + filter.getOperator());
        }
    }
}
