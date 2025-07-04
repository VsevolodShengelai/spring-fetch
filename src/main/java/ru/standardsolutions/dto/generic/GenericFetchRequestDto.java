package ru.standardsolutions.dto.generic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.standardsolutions.dto.generic.filters.Filters;
import ru.standardsolutions.dto.shared.SortDirection;
import ru.standardsolutions.request.PageRequest;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * Параметризуемый DTO для запросов с фильтрацией, пагинацией и сортировкой.
 *
 * @param <F> тип фильтров
 * @param <S> тип поля сортировки
 */
@Getter
@EqualsAndHashCode
@ToString
@Schema(description = "Параметризуемый DTO для fetch-запросов с фильтрацией и сортировкой")
public class GenericFetchRequestDto<F extends Filters, S extends Enum<S>> {

    @Schema(description = "Фильтры для поиска", requiredMode = NOT_REQUIRED)
    private final F filters;

    @Schema(description = "Параметры сортировки", requiredMode = NOT_REQUIRED)
    private final List<SortField<S>> sort;

    @Valid
    @Schema(description = "Параметры страницы", requiredMode = NOT_REQUIRED)
    private final PageRequest page;

    @JsonCreator
    public GenericFetchRequestDto(@JsonProperty("filters") F filters,
                                  @JsonProperty("sort") List<SortField<S>> sort,
                                  @JsonProperty("page") PageRequest page) {
        this.filters = filters;
        this.sort = sort;
        this.page = page;
    }

    /**
     * Поле сортировки.
     *
     * @param <S> тип поля сортировки
     */
    @Getter
    @ToString
    @EqualsAndHashCode
    @Schema(description = "Поле сортировки")
    public static class SortField<S> {

        @NotNull
        @Schema(description = "Имя поля сортировки", requiredMode = REQUIRED)
        private final S field;

        @NotNull
        @Schema(description = "Направление сортировки", requiredMode = REQUIRED)
        private final SortDirection direction;

        @JsonCreator
        public SortField(@JsonProperty("field") S field,
                         @JsonProperty("direction") SortDirection direction) {
            this.field = field;
            this.direction = direction;
        }
    }
}