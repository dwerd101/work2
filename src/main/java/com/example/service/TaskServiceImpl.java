package com.example.service;

import com.example.dao.TaskService;
import com.example.model.ProfileTask;
import com.example.repository.ProfileTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    ProfileTaskRepository repository;

    @Override
    public void save(ProfileTask profileTask) {
        repository.save(profileTask);
    }

    @Autowired
    public void setRepository(ProfileTaskRepository repository) {
        this.repository = repository;
    }
}
