package com.example.msgivetaskstoassignee.mapper;

import com.example.msgivetaskstoassignee.dao.entity.TelesaleEntity;
import com.example.msgivetaskstoassignee.model.TaskResponseDto;
import com.example.msgivetaskstoassignee.model.TelesaleRequestDto;
import com.example.msgivetaskstoassignee.model.TelesaleResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public abstract class TelesaleMapper {
   public abstract TelesaleEntity mapToEntity(TelesaleRequestDto telesaleReqDto);
    public abstract TelesaleRequestDto mapToReqDto(TelesaleEntity telesaleEntity);
    public abstract TelesaleResponseDto mapToRespDto(TelesaleEntity telesaleEntity);

}
