package ru.standardsolutions;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
                Predicate predicate = Operator.fromString(filter.getOperator())
                        .build(root, criteriaBuilder, filter);
                predicates.add(predicate);
            }
        }

        if ("OR".equalsIgnoreCase(groupOperator)) {
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        } else {
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
    }

    private Predicate createPredicate(FilterRequest condition, Class<?> fieldType, Path<?> path, CriteriaBuilder criteriaBuilder) {
        String operator = condition.getOperator();
        String value = condition.getValue();

        if (fieldType.equals(String.class)) {
            switch (operator) {
                case ":":
                    return criteriaBuilder.equal(path, value);
                case "like":
                    return criteriaBuilder.like(path.as(String.class), value);
            }
        } else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
            Integer intValue = Integer.parseInt(value);
            switch (operator) {
                case ":":
                    return criteriaBuilder.equal(path.as(Integer.class), intValue);
                case ">:":
                    return criteriaBuilder.greaterThanOrEqualTo(path.as(Integer.class), intValue);
                case "<:":
                    return criteriaBuilder.lessThanOrEqualTo(path.as(Integer.class), intValue);
            }
        } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
            Double doubleValue = Double.parseDouble(value);
            switch (operator) {
                case ":":
                    return criteriaBuilder.equal(path.as(Double.class), doubleValue);
                case ">:":
                    return criteriaBuilder.greaterThanOrEqualTo(path.as(Double.class), doubleValue);
                case "<:":
                    return criteriaBuilder.lessThanOrEqualTo(path.as(Double.class), doubleValue);
            }
        } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
            Boolean booleanValue = Boolean.parseBoolean(value);
            if (":".equals(operator)) {
                return criteriaBuilder.equal(path.as(Boolean.class), booleanValue);
            }
        } else if (fieldType.isEnum()) {
            Object enumValue = Enum.valueOf((Class<Enum>) fieldType, value.toUpperCase());
            if (":".equals(operator)) {
                return criteriaBuilder.equal(path.as(fieldType), enumValue);
            }
        }

        throw new UnsupportedOperationException("Operator " + operator + " is not supported for field type " + fieldType);
    }

}
