package com.example.msgivetaskstoassignee.dao.repository;

import com.example.msgivetaskstoassignee.dao.entity.TelesaleEntity;
import com.example.msgivetaskstoassignee.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TelesaleRepository extends JpaRepository<TelesaleEntity, Long> {
    @Query(value = "SELECT s " +
            "FROM TelesaleEntity s " +
            "WHERE s.id = (" +
            "    SELECT t.telesale.id " +
            "    FROM TaskEntity t " +
            "    WHERE t.status IN ('TO_DO', 'IN_PROGRESS') " +
            "    GROUP BY t.telesale.id " +
            "    ORDER BY COUNT(CASE WHEN t.status = 'TO_DO' THEN 1 ELSE 0 END) ASC, " +
            "             COUNT(CASE WHEN t.status = 'IN_PROGRESS' THEN 1 ELSE 0 END) ASC " +
            "    LIMIT 1" +
            ")")
    TelesaleEntity findTelesaleWithFewestToDoTasks();
}
