package com.example.msgivetaskstoassignee.mapper;

import com.example.msgivetaskstoassignee.dao.entity.TaskEntity;
import com.example.msgivetaskstoassignee.dao.entity.TelesaleEntity;
import com.example.msgivetaskstoassignee.dao.repository.TelesaleRepository;
import com.example.msgivetaskstoassignee.enums.Exceptions;
import com.example.msgivetaskstoassignee.exceptions.NotFoundException;
import com.example.msgivetaskstoassignee.model.TaskRequestDto;
import com.example.msgivetaskstoassignee.model.TaskResponseDto;
import com.example.msgivetaskstoassignee.model.TelesaleResponseDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
//@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Mapper(componentModel = "spring")
public abstract class TaskMapper {
    @Autowired
    private TelesaleRepository telesaleRepository;


//    @Mapping(target = "telesale", ignore = true)
    public abstract TaskEntity mapToEntity(TaskRequestDto taskRequestDto);

    public abstract TaskEntity mapToEntity(TaskResponseDto taskResponseDto);

    public abstract TaskResponseDto mapToRespDto(TaskEntity taskEntity);

}
