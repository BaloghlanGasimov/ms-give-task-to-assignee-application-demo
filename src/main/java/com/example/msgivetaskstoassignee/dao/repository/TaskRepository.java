package com.example.msgivetaskstoassignee.dao.repository;

import com.example.msgivetaskstoassignee.dao.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
