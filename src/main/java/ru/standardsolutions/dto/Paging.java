package ru.standardsolutions.dto;

import lombok.Data;

@Data
public class Paging {

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
     * Общее количество страниц.
     */
    private final Integer totalPages;

    /**
     * Общее количество ресурсов в коллекции.
     */
    private final Integer totalElements;
}