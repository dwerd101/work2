package com.example.service;

import com.example.dao.FieldMapper;
import com.example.dao.FieldService;
import com.example.dto.FieldValueDto;
import com.example.model.FieldValue;
import com.example.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FieldServiceImpl implements FieldService {

    FieldRepository fieldRep;
    @Override
    public List<FieldValueDto> findBySourceId(Long id) {
        List<FieldValue> fieldValueList = fieldRep.findBySourceId(id);

        Set<FieldValueDto> fieldValueDtoSet = fieldValueList
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
        return new ArrayList<>(fieldValueDtoSet);
    }

    @Autowired
    public void setFieldRep(FieldRepository fieldRep) {
        this.fieldRep = fieldRep;
    }
}
