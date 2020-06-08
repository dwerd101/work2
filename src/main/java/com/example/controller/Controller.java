package com.example.controller;
import com.example.dao.FieldService;
import com.example.dao.ProfileResultService;
import com.example.dao.TaskService;
import com.example.model.FieldDto;
import com.example.model.ProfileTask;
import com.example.service.FieldServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class Controller {

    private FieldService service;
    private ProfileResultService profileResultService;
    private TaskService taskService;

    public Controller(FieldService service, ProfileResultService profileResultService, TaskService taskService) {
        this.service = service;
        this.profileResultService = profileResultService;
        this.taskService = taskService;
    }

    @PostMapping("source")
    public void saveFieldValue(@RequestBody ProfileTask task,  Pageable pageable) {
        if(task.getSourceId()!=null && task.getId()!=null) {
            task.setStatus(ProfileTask.Status.RUNNING.toString());
            taskService.save(task);
            Page<FieldDto> fieldDtoList = service.findBySourceId(task.getSourceId(), pageable);
            Integer count = service.count(task.getSourceId());
            String domainText = "FIO";

            for (int i = 0; i < count; i++) {
                if (i == 0) {
                    List<FieldDto> fieldDto = fieldDtoList.getContent();
                    for (FieldDto f : fieldDto) {
                        //Отправка моего DTO во внешний сервис и затем получаю домен
                        profileResultService.saveProfileResult(f.getFieldId(), LocalDate.now(), domainText, null);
                    }
                } else {
                    fieldDtoList = service.findBySourceId(task.getSourceId(), PageRequest.of(i, pageable.getPageSize()));
                    List<FieldDto> fieldDto = fieldDtoList.getContent();
                    for (FieldDto f : fieldDto) {
                        //Отправка моего DTO во внешний сервис и затем получаю домен
                        profileResultService.saveProfileResult(f.getFieldId(), LocalDate.now(), domainText, null);
                    }
                }

            }
            task.setStatus(ProfileTask.Status.DONE.toString());
        }
        else {
            task.setStatus(ProfileTask.Status.ERROR.toString());
        }
        taskService.save(task);
    }
  //Если не устроит что fio выводит 2 вместо допустим 6, то я делаю цикл foreach не по самому объекут fieldDto, а внутри его по списку массива, fieldDto.getFieldValue().size()


}
