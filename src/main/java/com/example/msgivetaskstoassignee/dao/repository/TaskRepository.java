package com.example.msgivetaskstoassignee.dao.repository;

import com.example.msgivetaskstoassignee.dao.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    TaskEntity findBySubject(String subject);
    List<TaskEntity> getTaskEntitiesByExpiredDateNotNull();
}
