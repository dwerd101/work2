package com.example.dao;

import com.example.dto.FieldValueDto;
import com.example.model.Field;
import com.example.model.FieldValue;

import java.util.List;

public interface FieldService {
    List<FieldValueDto> findBySourceId(Long id);
}
