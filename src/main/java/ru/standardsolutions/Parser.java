package ru.standardsolutions;

import ru.standardsolutions.request.FilterRequest;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<FilterRequest> parseFilter(String filterString) {
        List<FilterRequest> filters = new ArrayList<>();
        if (filterString == null || filterString.isEmpty()) {
            return filters;
        }
        String[] filterPartsString = filterString.split(" AND ");
        for (String filterPartString : filterPartsString) {
//            for (String condition : andConditions) {
//                String[] parts = condition.split(":");
//                if (parts.length == 3) {
//                    String field = parts[0];
//                    String operator = parts[1];
//                    String value = parts[2];
//                    conditions.add(new FilterCondition(field, operator, value));
//                }
//            }
//            filterGroups.add(new FilterGroup("AND", conditions));
        }
        return filters;
    }
}
