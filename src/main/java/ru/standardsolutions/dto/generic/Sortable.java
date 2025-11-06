package ru.standardsolutions.dto.generic;

import java.util.Arrays;

public interface Sortable {

    String getValue();

    static <T extends Enum<T> & Sortable> T fromValue(String value, Class<T> enumType) {
        if (value == null) {
            throw new IllegalArgumentException("Поле сортировки не может быть null");
        }

        return Arrays.stream(enumType.getEnumConstants())
                .filter(field -> field.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Недопустимое поле сортировки: " + value + ". Допустимые значения: "
                        + Arrays.toString(Arrays.stream(enumType.getEnumConstants()).map(Sortable::getValue).toArray())
                ));
    }

}
