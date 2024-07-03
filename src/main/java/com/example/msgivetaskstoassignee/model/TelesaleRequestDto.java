package com.example.msgivetaskstoassignee.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TelesaleRequestDto {
    @Null
    private Long id;
    @NotNull
    private String name;
}
