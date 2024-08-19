package ru.standardsolutions;

import lombok.Data;

import java.util.List;

@Data
public class FetchResponse<T> {

    private final List<T> content;

    private final Paging paging;

    @Data
    public static class Paging {

        /**
         * Номер страницы, начинается с 1.
         */
        private final Integer pageNumber;

        /**
         * Размер страницы.
         */
        private final Integer pageSize;

        /**
         * Количество ресурсов на странице.
         */
        private final Integer numberOfElements;

        /**
         * Индекс первого ресурса на странице, начинается с 1.
         */
        private final Integer offset;

        /**
         * Признак первой страницы.
         */
        private final Boolean first;

        /**
         * Признак последней страницы.
         */
        private final Boolean last;

        /**
         * Общее количество страниц.
         */
        private final Integer totalPages;

        /**
         * Общее количество ресурсов в коллекции.
         */
        private final Integer totalElements;
    }
}
