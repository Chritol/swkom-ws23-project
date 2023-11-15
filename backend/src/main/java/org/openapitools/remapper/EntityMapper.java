package org.openapitools.remapper;

import org.openapitools.jackson.nullable.JsonNullable;

import java.time.OffsetDateTime;

public interface EntityMapper<EN, DTO> {
    DTO toDto(EN entity);

    EN toEntity(DTO dto);

}
