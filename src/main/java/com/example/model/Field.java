package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "field")

public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    //  @JsonProperty("id")
    private long id;

    @Column(name = "field_name")
    // @JsonProperty("field_name")
    private String fieldName;

    @Column(name = "size")
    // @JsonProperty("size")
    private String size;

    @Column(name = "type")
    // @JsonProperty("type")
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        if (id != field.id) return false;
        if (!Objects.equals(fieldName, field.fieldName)) return false;
        if (!Objects.equals(size, field.size)) return false;
        return Objects.equals(type, field.type);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (fieldName != null ? fieldName.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    /*  public Field(String fieldName, String size, String type) {
        this.fieldName=fieldName;
        this.size=size;
        this.type=type;
    }*/
}
