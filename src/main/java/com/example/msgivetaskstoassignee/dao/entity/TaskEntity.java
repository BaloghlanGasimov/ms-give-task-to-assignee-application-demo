package com.example.msgivetaskstoassignee.dao.entity;

import com.example.msgivetaskstoassignee.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tasks")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    private String reporter;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime expiredDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Integer taskDuration;
    @ManyToOne
    @JoinColumn(name = "telesale_id")
    private TelesaleEntity telesale;
}
