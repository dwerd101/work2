package com.example.repository;

import com.example.model.FieldValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface FieldValueRepository extends JpaRepository<FieldValue, Long> {

    @Query( nativeQuery = true,
            value= "select value from field_value where field_id = ?1",
            countQuery = "select count(1) from field_value where field_id = ?1"
    )
    Page<FieldValue> findByFieldId(Long id, Pageable pa);
}
