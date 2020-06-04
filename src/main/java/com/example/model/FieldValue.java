package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "field_value")
public class FieldValue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "field_id", referencedColumnName = "id")
    private Field fieldId;

    @Column(name = "value")
    private String value;

    public FieldValue(String value) {
        this.value = value;
    }
}
