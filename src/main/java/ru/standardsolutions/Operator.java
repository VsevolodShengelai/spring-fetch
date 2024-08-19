package ru.standardsolutions;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import ru.standardsolutions.request.FilterRequest;

@Slf4j
public enum Operator {

    EQUAL(":") {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest filter) {
            Path<?> fieldPath = getFieldPath(root, filter.getField());
            return cb.equal(fieldPath, filter.getValue());
        }
    },

    LIKE("like") {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest filter) {
            Path<?> fieldPath = getFieldPath(root, filter.getField());
            return cb.like(fieldPath.as(String.class), filter.getValue());
        }
    },

    GREATER_OR_EQUAL(">:") {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest filter) {
            Path<?> fieldPath = getFieldPath(root, filter.getField());
            Class<?> fieldType = fieldPath.getJavaType();
            if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
                Integer intValue = Integer.parseInt(filter.getValue());
                return cb.greaterThanOrEqualTo(fieldPath.as(Integer.class), intValue);
            } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
                Long longValue = Long.parseLong(filter.getValue());
                return cb.greaterThanOrEqualTo(fieldPath.as(Long.class), longValue);
            } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
                Double doubleValue = Double.parseDouble(filter.getValue());
                return cb.greaterThanOrEqualTo(fieldPath.as(Double.class), doubleValue);
            } else {
                String errorMessage = String.format("Неподдерживаемый тип %s для операции %s", fieldType, ">:");
                throw new IllegalArgumentException(errorMessage);
            }
        }
    },

    LESS_OR_EQUAL("<:") {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest filter) {
            Path<?> fieldPath = getFieldPath(root, filter.getField());
            Class<?> fieldType = fieldPath.getJavaType();
            if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
                Integer intValue = Integer.parseInt(filter.getValue());
                return cb.lessThanOrEqualTo(fieldPath.as(Integer.class), intValue);
            } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
                Long longValue = Long.parseLong(filter.getValue());
                return cb.lessThanOrEqualTo(fieldPath.as(Long.class), longValue);
            } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
                Double doubleValue = Double.parseDouble(filter.getValue());
                return cb.lessThanOrEqualTo(fieldPath.as(Double.class), doubleValue);
            } else {
                String errorMessage = String.format("Неподдерживаемый тип %s для операции %s", fieldType, ">:");
                throw new IllegalArgumentException(errorMessage);
            }
        }
    };

    private final String view;

    Operator(String view) {
        this.view = view;
    }

    public abstract <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request);

    public static Operator fromString(String view) {
        for (Operator op : Operator.values()) {
            if (view.equalsIgnoreCase(op.view)) {
                return op;
            }
        }
        throw new IllegalArgumentException("Неподдерживаемый оператор: " + view);
    }

    private static Path<?> getFieldPath(Root<?> root, String fieldName) {
        String[] parts = fieldName.split("\\.");
        Path<?> path = root;
        for (String part : parts) {
            path = path.get(part);
        }
        return path;
    }
}