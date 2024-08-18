package ru.standardsolutions;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import ru.standardsolutions.request.FetchRequest;

public enum SortDirection {
    ASC {
        public <T> Order build(Root<T> root, CriteriaBuilder cb, FetchRequest.Sort request) {
            return cb.asc(root.get(request.getField()));
        }
    }, DESC {
        public <T> Order build(Root<T> root, CriteriaBuilder cb, FetchRequest.Sort request) {
            return cb.desc(root.get(request.getField()));
        }
    };

    public abstract <T> Order build(Root<T> root, CriteriaBuilder cb, FetchRequest.Sort request);
}