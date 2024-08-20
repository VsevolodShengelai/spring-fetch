package ru.standardsolutions;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import ru.standardsolutions.request.FetchRequest;
import ru.standardsolutions.request.FilterRequest;

import java.util.ArrayList;
import java.util.List;

import static ru.standardsolutions.Operator.*;

/**
 * Спецификация для набора фильтров.
 * @param <T>
 */
public class FetchSpecification<T> implements Specification<T> {

    /**
     * Запрос с фильтрацией, сортировкой и пагинацией.
     */
    private final FetchRequest fetchRequest;

    public FetchSpecification(FetchRequest fetchRequest) {
        this.fetchRequest = fetchRequest;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return buildPredicates(root, criteriaBuilder, fetchRequest.getFilters(), AND);
    }

    private Predicate buildPredicates(Root<T> root, CriteriaBuilder criteriaBuilder, List<FilterRequest> filters,
                                      Operator groupOperator) {
        List<Predicate> predicates = new ArrayList<>();
        for (FilterRequest filter : filters) {
            Operator operator = fromString(filter.getOperator());
            boolean isGroupOperator = (operator == OR || operator == AND);
            if (isGroupOperator) {
                Predicate groupPredicate = buildPredicates(root, criteriaBuilder, filter.getFilters(), operator);
                predicates.add(groupPredicate);
            } else {
                Predicate predicate = operator.createPredicate(root, criteriaBuilder, filter);
                predicates.add(predicate);
            }
        }

        if (groupOperator == OR) {
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        } else {
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
    }
}
