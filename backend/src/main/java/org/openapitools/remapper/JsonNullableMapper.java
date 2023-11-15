package org.openapitools.remapper;

import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper
public interface JsonNullableMapper {
    default <T> JsonNullable<T> map(T value) {
        return JsonNullable.of(value);
    }

    default <T> T map(JsonNullable<T> jsonNullable) {
        return jsonNullable.orElse(null);
    }
}