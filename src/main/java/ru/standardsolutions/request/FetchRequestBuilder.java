package ru.standardsolutions.request;

import java.util.ArrayList;
import java.util.List;

/**
 * Построитель для создания экземпляров {@link FetchRequest} с удобным API.
 */
public class FetchRequestBuilder {
    private List<FilterRequest> filters = new ArrayList<>();
    private List<SortRequest> sort = new ArrayList<>();
    private PageRequest page;

    /**
     * Создает новый экземпляр {@link FetchRequestBuilder}.
     */
    public FetchRequestBuilder() {
    }

    /**
     * Добавляет фильтр в запрос.
     *
     * @param field    поле для фильтрации
     * @param operator оператор
     * @param value    значение для фильтрации
     * @return этот экземпляр построителя
     */
    public FetchRequestBuilder filter(String field, String operator, String value) {
        if (field != null && operator != null && value != null) {
            filters.add(new FilterRequest(field, operator, value, List.of()));
        }
        return this;
    }

    /**
     * Добавляет фильтр с вложенными фильтрами в запрос.
     *
     * @param field    поле для фильтрации
     * @param operator оператор
     * @param value    значение для фильтрации
     * @param filters  вложенные фильтры
     * @return этот экземпляр построителя
     */
    public FetchRequestBuilder filter(String field, String operator, String value, List<FilterRequest> filters) {
        if (field != null && operator != null && value != null && filters != null) {
            this.filters.add(new FilterRequest(field, operator, value, filters));
        }
        return this;
    }

    /**
     * Добавляет фильтр равенства.
     *
     * @param field поле для фильтрации
     * @param value значение для сравнения
     * @return этот экземпляр построителя
     */
    public FetchRequestBuilder equals(String field, String value) {
        return filter(field, ":", value);
    }

    /**
     * Добавляет фильтр неравенства.
     *
     * @param field поле для фильтрации
     * @param value значение для сравнения
     * @return этот экземпляр построителя
     */
    public FetchRequestBuilder notEquals(String field, String value) {
        return filter(field, "!:", value);
    }

    /**
     * Добавляет фильтр "больше".
     *
     * @param field поле для фильтрации
     * @param value значение для сравнения
     * @return этот экземпляр построителя
     */
    public FetchRequestBuilder greaterThan(String field, String value) {
        return filter(field, ">", value);
    }

    /**
     * Добавляет фильтр "больше или равно".
     *
     * @param field поле для фильтрации
     * @param value значение для сравнения
     * @return этот экземпляр построителя
     */
    public FetchRequestBuilder greaterThanOrEquals(String field, String value) {
        return filter(field, ">:", value);
    }

    /**
     * Добавляет фильтр "меньше".
     *
     * @param field поле для фильтрации
     * @param value значение для сравнения
     * @return этот экземпляр построителя
     */
    public FetchRequestBuilder lessThan(String field, String value) {
        return filter(field, "<", value);
    }

    /**
     * Добавляет фильтр "меньше или равно".
     *
     * @param field поле для фильтрации
     * @param value значение для сравнения
     * @return этот экземпляр построителя
     */
    public FetchRequestBuilder lessThanOrEquals(String field, String value) {
        return filter(field, "<:", value);
    }

    /**
     * Добавляет фильтр поиска по шаблону.
     *
     * @param field поле для фильтрации
     * @param value шаблон для поиска
     * @return этот экземпляр построителя
     */
    public FetchRequestBuilder like(String field, String value) {
        return filter(field, "like", value);
    }

    /**
     * Добавляет фильтр вхождения в список значений.
     *
     * @param field  поле для фильтрации
     * @param values значения для сравнения
     * @return этот экземпляр построителя
     */
    public FetchRequestBuilder in(String field, String... values) {
        if (field != null && values != null && values.length > 0) {
            return filter(field, "in", String.join(",", values));
        }
        return this;
    }

    /**
     * Добавляет фильтр отрицания вхождения в список значений.
     *
     * @param field  поле для фильтрации
     * @param values значения для сравнения
     * @return этот экземпляр построителя
     */
    public FetchRequestBuilder notIn(String field, String... values) {
        if (field != null && values != null && values.length > 0) {
            return filter(field, "not in", String.join(",", values));
        }
        return this;
    }

    /**
     * Добавляет критерий сортировки.
     *
     * @param field     поле для сортировки
     * @param direction направление сортировки (ASC или DESC)
     * @return этот экземпляр построителя
     */
    public FetchRequestBuilder sort(String field, String direction) {
        if (field != null && direction != null) {
            sort.add(new SortRequest(field, direction));
        }
        return this;
    }

    /**
     * Добавляет критерий сортировки по возрастанию.
     *
     * @param field поле для сортировки
     * @return этот экземпляр построителя
     */
    public FetchRequestBuilder sortAsc(String field) {
        return sort(field, "ASC");
    }

    /**
     * Добавляет критерий сортировки по убыванию.
     *
     * @param field поле для сортировки
     * @return этот экземпляр построителя
     */
    public FetchRequestBuilder sortDesc(String field) {
        return sort(field, "DESC");
    }

    /**
     * Устанавливает параметры пагинации.
     *
     * @param number номер страницы (начиная с 1)
     * @param size   размер страницы
     * @return этот экземпляр построителя
     */
    public FetchRequestBuilder page(int number, int size) {
        if (number > 0 && size > 0) {
            this.page = new PageRequest(number, size);
        }
        return this;
    }

    /**
     * Создает группу фильтров с логическим И.
     *
     * @param filters список фильтров для объединения через И
     * @return этот экземпляр построителя
     */
    public FetchRequestBuilder and(List<FilterRequest> filters) {
        if (filters != null && !filters.isEmpty()) {
            this.filters.add(new FilterRequest(null, "AND", null, filters));
        }
        return this;
    }

    /**
     * Создает группу фильтров с логическим ИЛИ.
     *
     * @param filters список фильтров для объединения через ИЛИ
     * @return этот экземпляр построителя
     */
    public FetchRequestBuilder or(List<FilterRequest> filters) {
        if (filters != null && !filters.isEmpty()) {
            this.filters.add(new FilterRequest(null, "OR", null, filters));
        }
        return this;
    }

    /**
     * Создает новый экземпляр {@link FetchRequest} с настроенными параметрами.
     *
     * @return новый экземпляр {@link FetchRequest}
     */
    public FetchRequest build() {
        return new FetchRequest(filters, sort, page);
    }
} 