package com.example.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Immutable
@Data
@Table(name = "field_view")
public class FieldDto {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long fieldId;
    @Column(name="field_name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "size")
    private String size;
    @Transient
    List<String> fieldValue;
    @Column(name = "source_id")
    private Long sourceId;



    public FieldDto(long fieldId, String name, String type, String size, Long sourceId) {
        this.fieldId = fieldId;
        this.name = name;
        this.type = type;
        this.size = size;
        this.sourceId = sourceId;

    }

    public FieldDto() {
        fieldValue = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldDto that = (FieldDto) o;

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
