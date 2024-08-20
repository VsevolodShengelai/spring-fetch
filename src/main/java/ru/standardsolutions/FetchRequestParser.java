package ru.standardsolutions;

import ru.standardsolutions.request.FilterRequest;
import ru.standardsolutions.request.SortRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Парсер запроса по строке.
 * <p>
 * Предоставляет возможность получить объекты параметров фильтрации, сортировки
 * и пагинации.
 */
public class FetchRequestParser {

    private static final String FILTER_PATTERN =
            "(?<field>[a-zA-Z_][a-zA-Z_0-9]*)\\s*(?<operator><:|>:|<|>|:|like|in)\\s*(?<value>\"[^\"]*\"|[^\\s,]+)";

    private static final String LOGICAL_OPERATOR_PATTERN = "and|or";

    public static List<FilterRequest> parseFilter(String filterString) {
        List<FilterRequest> filters = new ArrayList<>();
        if (filterString == null || filterString.isEmpty()) {
            return filters;
        }
        String[] filterParts = filterString.split("(?i) AND ");
        for (String filterPart : filterParts) {
            Pattern pattern = Pattern.compile(FILTER_PATTERN, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(filterPart);
            if (!matcher.find()) {
                continue;
            }
            String field = matcher.group("field");
            String operator = matcher.group("operator");
            String value = matcher.group("value").replace("\"", "");
            if (operator.equals("in")) {
                value = value.replace("(", "").replace(")", ""); // Удаляем скобки
            }
            FilterRequest filterRequest = new FilterRequest(field, operator, value, List.of());
            filters.add(filterRequest);
        }
        return filters;
    }

    public static List<SortRequest> parseSort(String sortString) {
        List<SortRequest> sortRequest = new ArrayList<>();
        String[] sortFields = sortString.split(",");
        for (String sortField : sortFields) {
            String[] parts = sortField.split(":");
            String field = parts[0];
            String direction = (parts.length > 1) ? parts[1] : "ASC";
            sortRequest.add(new SortRequest(field, direction));
        }
        return sortRequest;
    }
}
