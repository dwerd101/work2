package com.example.service;

import com.example.dao.FieldService;
import com.example.dto.FieldValueDto;
import com.example.model.Field;
import com.example.model.FieldValue;
import com.example.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class FieldServiceImpl implements FieldService {

    FieldRepository fieldRep;

    JdbcTemplate jdbcTemplate;

    @Override
    public List<FieldValueDto> findBySourceId(Long id) {
        List<Field> fieldValueList = fieldRep.findBySourceId(id);

       /* Set<FieldValueDto> fieldValueDtoSet = fieldValueList
                .stream()
                .map(fieldValue -> {
                    return FieldMapper
                            .INSTANCE
                            .returnDto(fieldValue.getFieldId());

                }).collect(Collectors.toSet());

        for (FieldValueDto fieldValueDto: fieldValueDtoSet
             ) {
            for (FieldValue fieldValue: fieldValueList
                 ) {
                fieldValueDto.getFieldValue().add(fieldValue.getValue());
            }
        }
        return new ArrayList<>(fieldValueDtoSet);*/
        return null;
    }

    @Override
    public List<FieldValueDto> findBySourceByJdbcId(Long id) {
        List<Field> fieldList = findBySourceAndReturnListField(id);
        List<FieldValue> fieldValueList = new ArrayList<>();
        List<FieldValueDto> fieldValueDtoList = new ArrayList<>();
        for (Field field: fieldList
              ) {
            fieldValueList.addAll(findByFieldIdAndReturnList(field.getId()));
            List<String> value = new ArrayList<>();
            for (FieldValue fieldValue: fieldValueList
                 ) {
                value.add(fieldValue.getValue());
            }
            fieldValueDtoList.add(
                    new FieldValueDto(field.getId(),field.getFieldName(),
                            field.getType(), field.getSize(), value ));
            fieldValueList.clear();
            //Задействовать FieldValueDto и добавить все поля и конвертнуть FieldValue в String "getText" Не забудь очистить колллекцию fieldValueList
        }
        return fieldValueDtoList;

    }


    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setFieldRep(FieldRepository fieldRep) {
        this.fieldRep = fieldRep;
    }

    //Выносим отдельно реализацию, смотрим по страничкам и возвращаем
    private List<Field> findBySourceAndReturnListField(Long id) {
        Pageable pageable = PageRequest.of(0, 20);
        long page = pageable.getOffset();
        //language=SQL
        String FIND_BY_SOURCE_ID = "select field.id, type, size, field.field_name\n" +
                "from field\n" +
                "inner join public.table  on field.table_id = public.table.id " +
                "inner join owner o on public.table.owner_id = o.id\n" +
                "inner join source s on o.source_id = s.id\n" +
                "where s.id=?" +
                "LIMIT " + pageable.getPageSize() + " " + //20
                "OFFSET " + pageable.getOffset(); //0

        Set<Field> fieldList = new LinkedHashSet<>();
        boolean pageNotIsEmpty = true;
        while (pageNotIsEmpty) {
            List<Field> fieldValueList = jdbcTemplate.query(FIND_BY_SOURCE_ID,
                    preparedStatement -> preparedStatement.setLong(1, id),
                    (resultSet, i) -> {
                        Long fieldId = resultSet.getLong("id");
                        String type = resultSet.getString("type");
                        String size = resultSet.getString("size");
                        String fieldName = resultSet.getString("field_name");

                        return new Field(fieldId, type, size, fieldName);
                    });

            if (fieldValueList.isEmpty()) {
                pageNotIsEmpty = false;
            } else {
                fieldList.addAll(fieldValueList);
                fieldValueList.clear();
                FIND_BY_SOURCE_ID = FIND_BY_SOURCE_ID.replaceAll("OFFSET " + page,
                        "OFFSET " + ++page);
            }

        }
        return new ArrayList<>(fieldList);
    }

    private List<FieldValue> findByFieldIdAndReturnList(long fieldId) {
        //language=SQL
        String FIND_VALUE = "select value from field_value where field_id = ?";
        return jdbcTemplate.query(FIND_VALUE,
                preparedStatement -> preparedStatement.setLong(1, fieldId),
                (resultSet, i) -> {
                    String value = resultSet.getString(1);
                    return new FieldValue(value);
                });
    }


}
