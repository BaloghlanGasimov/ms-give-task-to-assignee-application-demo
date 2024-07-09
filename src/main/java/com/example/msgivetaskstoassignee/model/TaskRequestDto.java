package com.example.msgivetaskstoassignee.model;

import com.example.msgivetaskstoassignee.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TaskRequestDto {
    @NotBlank
    private String subject;
    @NotBlank
    private String description;
    @NotBlank
    private String reporter;
    private Long telesaleId;
    private Integer taskDuration;
}
