package com.example.repository;

import com.example.model.ProfileTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileTaskRepository extends JpaRepository<ProfileTask, Long> {

}
