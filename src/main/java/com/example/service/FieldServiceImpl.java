package com.example.service;

import com.example.dao.FieldService;
import com.example.model.FieldDto;
import com.example.model.Field;
import com.example.model.FieldValue;
import com.example.repository.FieldRepository;
import com.example.repository.FieldValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class FieldServiceImpl implements FieldService {

    FieldRepository fieldRep;
    FieldValueRepository fieldValueRepository;

    JdbcTemplate jdbcTemplate;

    @Override
    public Page<FieldDto> findBySourceId(Long id, Pageable pageable) {

        //language=SQL
        String FIND_VALUE = "select value, field_id  from field_value where field_id = ?";


        Page<FieldDto> fieldPage = fieldRep.findBySourceId(id,pageable);

        List<FieldDto> fieldDtos = fieldPage.getContent();
        List<FieldValue> valueList = new ArrayList<>();
        for (FieldDto f: fieldDtos) {
           List<FieldValue> tempFieldValueList = jdbcTemplate.query(FIND_VALUE,
                    preparedStatement -> preparedStatement.setLong(1, f.getFieldId()),
                    (resultSet, i) -> {

                        Field field = new Field();
                        String value = resultSet.getString(1);
                        field.setId(resultSet.getLong(2));
                        return new FieldValue(value, field);
                    });
           valueList.addAll(tempFieldValueList);
        }
        for(int i=0; i<valueList.size(); i++) {
            for(int k=0; k<fieldDtos.size(); k++) {
                if(valueList.get(i).getFieldId().getId()==fieldDtos.get(k).getFieldId()) {
                    fieldDtos.get(k).getFieldValue().add(valueList.get(i).getValue());
                }
            }
        }


         return new PageImpl<>(fieldDtos, pageable, fieldDtos.size());
    }

    /*  @Override
    public List<FieldDto> findBySourceId(Long id, Pageable pageable) {
       // List<Field> fieldValueList = fieldRep.findBySourceId(id);

       *//* Set<FieldValueDto> fieldValueDtoSet = fieldValueList
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
        return new ArrayList<>(fieldValueDtoSet);*//*
        return null;
    }*/



    @Override
    public Integer count(Long id) {
       //language=sql
        final String SQL_COUNT = "select count(1) " +
                "from field_view\n" +
                "where source_id=?";
        Integer count = jdbcTemplate.queryForObject(SQL_COUNT, new Object[] {id}, Integer.class);
        return count;
    }


    @Override
    public List<FieldDto> findBySourceByJdbcId(Long id) {
      /*  List<Field> fieldList = findBySourceAndReturnListField(id);
        List<FieldValue> fieldValueList = new ArrayList<>();
        List<FieldDto> fieldDtoList = new ArrayList<>();
        for (Field field: fieldList) {
            fieldValueList.addAll(findByFieldIdAndReturnList(field.getId()));
            List<String> value = new ArrayList<>();
            for (FieldValue fieldValue: fieldValueList) {
                value.add(fieldValue.getValue());
            }
            fieldDtoList.add(
                    new FieldDto(field.getId(),field.getFieldName(),
                            field.getType(), field.getSize(), value ));
            fieldValueList.clear();
        }
        return fieldDtoList;*/

   return null; }


    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setFieldValueRepository(FieldValueRepository fieldValueRepository) {
        this.fieldValueRepository = fieldValueRepository;
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
