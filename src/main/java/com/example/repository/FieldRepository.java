package com.example.repository;

import com.example.model.Field;
import com.example.model.FieldValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FieldRepository extends JpaRepository<FieldValue, Long> {
    @Query( nativeQuery = true,
            value= "select *\n" +
                    "from field_value inner join field f on field_value.field_id = f.id\n" +
                    "inner join public.table t on f.tables_id = t.id inner join owner o on t.owner_id = o.id\n" +
                    "inner join source s on o.source_id = s.id\n" +
                    "where s.id=?1"
    )
    List<FieldValue> findBySourceId(Long id);
}
