package com.example.controller;
import com.example.dao.FieldService;
import com.example.dao.ProfileResultService;
import com.example.dto.FieldValueDto;
import com.example.model.FieldValue;
import com.example.service.FieldServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class Controller {

    private FieldServiceImpl service;
    private ProfileResultService profileResultService;

    public Controller(FieldServiceImpl service, ProfileResultService profileResultService) {
        this.service = service;
        this.profileResultService = profileResultService;
    }

    @PostMapping("source/{id}/profile")
    public void saveFieldValue(@PathVariable("id") Long id) {
        List<FieldValueDto> fieldValueDtoList = service.findBySourceByJdbcId(id);
        String domainText = "FIO";
        for(FieldValueDto f: fieldValueDtoList) {
            //Отправка моего DTO во внешний сервис и затем получаю домен
            profileResultService.saveProfileResult(f.getFieldId(), LocalDate.now(),domainText, null);

        }
       // return fieldValueDtoList;
    }


}
