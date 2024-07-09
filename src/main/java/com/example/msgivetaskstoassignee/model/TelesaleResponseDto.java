package com.example.msgivetaskstoassignee.model;

import com.example.msgivetaskstoassignee.dao.entity.TelesaleEntity;
import com.example.msgivetaskstoassignee.enums.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TelesaleResponseDto {
    @Null
    private Long id;
    @NotNull
    private String name;
    private List<TaskResponseDto> tasks;

    @Data
     public static class TaskResponseDto {
        private Long id;
        private String subject;
        private String description;
        private LocalDateTime createdDate;
        private LocalDateTime expiredDate;
        private Integer remainingTime;
        private Status status;
    }
}
