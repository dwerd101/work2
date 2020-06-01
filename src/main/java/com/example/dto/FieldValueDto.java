package com.example.dto;

import com.example.model.Field;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FieldValueDto {


    private Long fieldId;
    private String name,type,size;
    List<String> fieldValue;

    public FieldValueDto(long fieldId, String name, String type, String size) {
        this.fieldId = fieldId;
        this.name = name;
        this.type = type;
        this.size = size;

    }

    public FieldValueDto() {
        fieldValue = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldValueDto that = (FieldValueDto) o;

        if (fieldId != null ? !fieldId.equals(that.fieldId) : that.fieldId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (size != null ? !size.equals(that.size) : that.size != null) return false;
        return fieldValue != null ? fieldValue.equals(that.fieldValue) : that.fieldValue == null;
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
