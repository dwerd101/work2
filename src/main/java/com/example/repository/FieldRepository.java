package com.example.repository;

import com.example.model.Field;
import com.example.model.FieldValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {
    @Query( nativeQuery = true,
            value= "select type, size, field.field_name\n" +
                    "                    from field\n" +
                    "                    inner join public.table  on field.table_id = public.table.id inner join owner o on public.table.owner_id = o.id\n" +
                    "                    inner join source s on o.source_id = s.id\n" +
                    "                    where s.id=?1"
    )
    List<Field> findBySourceId(Long id);
}
