package com.example.msgivetaskstoassignee.service;

import com.example.msgivetaskstoassignee.dao.entity.TaskEntity;
import com.example.msgivetaskstoassignee.dao.entity.TelesaleEntity;
import com.example.msgivetaskstoassignee.dao.repository.TaskRepository;
import com.example.msgivetaskstoassignee.dao.repository.TelesaleRepository;
import com.example.msgivetaskstoassignee.enums.Exceptions;
import com.example.msgivetaskstoassignee.exceptions.NotFoundException;
import com.example.msgivetaskstoassignee.mapper.TelesaleMapper;
import com.example.msgivetaskstoassignee.model.TaskResponseDto;
import com.example.msgivetaskstoassignee.model.TelesaleRequestDto;
import com.example.msgivetaskstoassignee.model.TelesaleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class TelesaleService {
    private final TaskRepository taskRepository;
    private final TelesaleRepository telesaleRepository;
    private final TelesaleMapper telesaleMapper;

    public List<TelesaleResponseDto> getAllTelesales() {
        log.info("ActionLog.getAllTelesales.start");
        List<TelesaleEntity> telesales = telesaleRepository.findAll();
        List<TelesaleResponseDto> telesaleRespDtos = telesales.stream().map(telesaleMapper::mapToRespDto).toList();
        log.info("ActionLog.getAllTelesales.end");
        return telesaleRespDtos;
    }
    public TelesaleResponseDto getTelesaleById(Long id) {
        log.info("ActionLog.getTelesaleById.start telesaleId {}",id);
        TelesaleEntity telesale = findTelesale(id);
        TelesaleResponseDto telesaleRespDto = telesaleMapper.mapToRespDto(telesale);
        log.info("ActionLog.getTelesaleById.end telesaleId {}",id);
        return telesaleRespDto;
    }
    public void startTelesaleTask(Long id, Long taskId){
        log.info("ActionLog.startTelesaleTask.start telesaleId {} taskId {}",id,taskId);

        TelesaleEntity telesale = findTelesale(id);
        List<TaskEntity> taskEntities = telesale.getTasks();
        TaskEntity task =  taskEntities.stream().filter(t->t.getId() == taskId).
                findFirst().
                orElseThrow(()->new  NotFoundException(
                        Exceptions.TASK_FOR_TELESALE_NOT_FOUND.toString(),
                        String.format("ActionLog.startTelesaleTask.error telesaleId {%d} taskId {%d}",id,taskId))
                );
        task.setExpiredDate(LocalDateTime.now().plusHours(task.getTaskDuration()));
        taskRepository.save(task);
        log.info("ActionLog.startTelesaleTask.end telesaleId {} taskId {}",id,taskId);
    }
    public void saveTelesale(TelesaleRequestDto telesaleReqDto){
        log.info("ActionLog.saveTelesale.start telesale {}", telesaleReqDto);
        TelesaleEntity telesale = telesaleMapper.mapToEntity(telesaleReqDto);
        telesaleRepository.save(telesale);
        log.info("ActionLog.saveTelesale.end telesale {}", telesaleReqDto);
    }
    public void editTelesale(Long id, TelesaleRequestDto telesaleReqDto){
        log.info("ActionLog.editTelesale.start telesaleId {} telesale {}",id, telesaleReqDto);
        TelesaleEntity telesale = findTelesale(id);
        if(telesaleReqDto.getName()!=null){
            telesale.setName(telesaleReqDto.getName());
        }
        telesaleRepository.save(telesale);
        log.info("ActionLog.editTelesale.end telesaleId {} telesale {}",id, telesaleReqDto);
    }
    public TelesaleRequestDto deleteTelesale(Long id){
        log.info("ActionLog.deleteTelesale.start telesaleId {}",id);
        TelesaleEntity telesale = findTelesale(id);
        TelesaleRequestDto telesaleReqDto = telesaleMapper.mapToReqDto(telesale);
        telesaleRepository.deleteById(id);
        log.info("ActionLog.deleteTelesale.end telesaleId {}",id);
        return telesaleReqDto;
    }
    public TelesaleEntity findTelesale(Long id) {
        TelesaleEntity telesale = telesaleRepository.findById(id).
                orElseThrow(() -> new NotFoundException(
                        Exceptions.TELESALE_NOT_FOUND.toString(),
                        String.format("Error ActionLog.findTelesale telesaleId {%d}", id)
                ));
        return telesale;
    }
}
