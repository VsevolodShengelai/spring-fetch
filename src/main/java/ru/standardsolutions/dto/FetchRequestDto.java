package ru.standardsolutions.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class FetchRequestDto {

    private final List<Filter> filters;

    private final List<Sort> sort;

    private final Page page;

    @Data
    @RequiredArgsConstructor
    public static class Sort {

        @NotNull
        private final String field;

        @NotNull
        private final SortDirection direction;
    }

    @Data
    @RequiredArgsConstructor
    public static class Page {

        @NotNull
        private final Integer offset = 0;

        @NotNull
        private final Integer limit = 100;
    }

    @Data
    @RequiredArgsConstructor
    public static class Filter {

        private final String field;

        private final String operator;

        private final String value;

        private final List<String> values;
    }
}