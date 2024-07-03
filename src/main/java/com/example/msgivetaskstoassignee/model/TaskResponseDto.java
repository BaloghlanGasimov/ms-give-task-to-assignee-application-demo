package com.example.msgivetaskstoassignee.model;

import com.example.msgivetaskstoassignee.dao.entity.TelesaleEntity;
import com.example.msgivetaskstoassignee.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TaskResponseDto {
    private Long id;
    private String subject;
    private String description;
    private String reporter;
    private LocalDateTime createdDate;
    private Status status;
    private TelesaleResponseDto telesale;

    @Data
    public static class TelesaleResponseDto{
        private Long id;
        private String name;
    }
}
