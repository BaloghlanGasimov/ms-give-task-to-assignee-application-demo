package com.example.msgivetaskstoassignee.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.scheduling.config.Task;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "telesales")
public class TelesaleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "telesale",cascade = CascadeType.ALL)
    private List<TaskEntity> tasks;
}
