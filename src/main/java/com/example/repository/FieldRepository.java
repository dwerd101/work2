package com.example.repository;

import com.example.model.FieldDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<FieldDto, Long> {
   /* @Query( nativeQuery = true,
            value= "select type, size, field.field_name\n" +
                    "                    from field\n" +
                    "                    inner join public.table  on field.table_id = public.table.id inner join owner o on public.table.owner_id = o.id\n" +
                    "                    inner join source s on o.source_id = s.id\n" +
                    "                    where s.id=?1",

            countQuery = "select count(1)" +
            "                    from field\n" +
            "                    inner join public.table  on field.table_id = public.table.id inner join owner o on public.table.owner_id = o.id\n" +
            "                    inner join source s on o.source_id = s.id\n" +
            "                    where s.id=?1"
    )
    Page<Field> findBySourceId(Long id, Pageable page);*/

    @Query(nativeQuery = true,
                    value = "select id, field_name, type, size, source_id\n" +
                            "from field_view\n" +
                            "where source_id=?1",
            countQuery = "select count(1)" +
                    "from field_view\n" +
                    "where source_id=?1" )
    Page<FieldDto> findBySourceId(Long id, Pageable page);




}
