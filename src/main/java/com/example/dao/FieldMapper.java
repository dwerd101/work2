package com.example.dao;

import com.example.dto.FieldValueDto;
import com.example.model.Field;
import com.example.model.FieldValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "FieldValue")
public interface FieldMapper {
    FieldMapper INSTANCE = Mappers.getMapper(FieldMapper.class);
    @Mappings( {
            @Mapping(target = "fieldId", source = "field.id"),
            @Mapping(target = "name", source = "field.fieldName"),
            @Mapping(target = "type", source = "field.type"),
            @Mapping(target = "size", source = "field.size")
    })
    FieldValueDto returnDto(Field field);
}
