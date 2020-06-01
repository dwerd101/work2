package com.example.controller;


import com.example.dao.FieldService;
import com.example.dao.ProfileResultService;
import com.example.dto.FieldValueDto;
import com.example.model.FieldValue;
import com.example.service.FieldServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    private FieldServiceImpl service;
    private ProfileResultService profileResultService;

    public Controller(FieldServiceImpl service, ProfileResultService profileResultService) {
        this.service = service;
        this.profileResultService = profileResultService;
    }

    @GetMapping("source/{id}/profile")
    public List<FieldValueDto> returnFieldValue(@PathVariable("id") Long id) {
        List<FieldValueDto> fieldValueDtoList = service.findBySourceId(id);
        StringBuilder domain = new StringBuilder();
        for(FieldValueDto f: fieldValueDtoList) {

            for(String text: f.getFieldValue()) {
                domain.append(text).append("\n");
            }

        }
        profileResultService.saveProfileResultDomain(domain.toString(), id);
        return fieldValueDtoList;
    }


}
