package com.example.dao;

import com.example.model.FieldDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface FieldService {
    Page<FieldDto> findBySourceId(Long id , Pageable pageable);
   // Page returnPages()
    List<FieldDto> findBySourceByJdbcId(Long id);
     Integer countAllFieldDto(Long id);
}
