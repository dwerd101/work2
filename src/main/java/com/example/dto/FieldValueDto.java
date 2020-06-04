package com.example.dto;

import com.example.model.Field;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class FieldValueDto {


    private Long fieldId;
    private String name,type,size;
    List<String> fieldValue;

    public FieldValueDto(long fieldId, String name, String type, String size, List<String> fieldValue) {
        this.fieldId = fieldId;
        this.name = name;
        this.type = type;
        this.size = size;
        this.fieldValue=fieldValue;

    }

    public FieldValueDto() {
        fieldValue = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldValueDto that = (FieldValueDto) o;

        if (!Objects.equals(fieldId, that.fieldId)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(type, that.type)) return false;
        if (!Objects.equals(size, that.size)) return false;
        return Objects.equals(fieldValue, that.fieldValue);
    }

    @Override
    public int hashCode() {
        int result = fieldId != null ? fieldId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (fieldValue != null ? fieldValue.hashCode() : 0);
        return result;
    }
}
