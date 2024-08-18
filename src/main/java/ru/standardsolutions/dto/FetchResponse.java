package ru.standardsolutions.dto;

import lombok.Data;

@Data
public class FetchResponse<T> {
    private final T data;
    private final Paging paging;
}
