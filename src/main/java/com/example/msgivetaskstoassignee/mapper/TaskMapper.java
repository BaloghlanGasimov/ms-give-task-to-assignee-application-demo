package com.example.msgivetaskstoassignee.mapper;

import com.example.msgivetaskstoassignee.dao.entity.TaskEntity;
import com.example.msgivetaskstoassignee.model.TaskRequestDto;
import com.example.msgivetaskstoassignee.model.TaskResponseDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

@NoArgsConstructor
@Mapper(componentModel = "spring")
public abstract class TaskMapper {
    public abstract TaskEntity mapToEntity(TaskRequestDto taskRequestDto);
    @Mapping(target = "remainingTime",source = "taskEntity",qualifiedByName = "timeToHours")
    public abstract TaskResponseDto mapToRespDto(TaskEntity taskEntity);

    @Named("timeToHours")
    public Integer timeToHours(TaskEntity task){
        if(task.getExpiredDate()==null){
            return null;
        }

        Long remainingHour = Duration.between(LocalDateTime.now(),task.getExpiredDate()).toHours();
        return Integer.valueOf(remainingHour.intValue());
    }
}
