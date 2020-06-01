package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
}
