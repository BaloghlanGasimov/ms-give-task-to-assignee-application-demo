package com.example.msgivetaskstoassignee.service;

import com.example.msgivetaskstoassignee.dao.entity.TaskEntity;
import com.example.msgivetaskstoassignee.dao.repository.TaskRepository;
import com.example.msgivetaskstoassignee.enums.Exceptions;
import com.example.msgivetaskstoassignee.enums.Status;
import com.example.msgivetaskstoassignee.exceptions.NotFoundException;
import com.example.msgivetaskstoassignee.mapper.TaskMapper;
import com.example.msgivetaskstoassignee.model.TaskRequestDto;
import com.example.msgivetaskstoassignee.model.TaskResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<TaskResponseDto> getAllTasks(){
        log.info("ActionLog.getAllTasks.start");
        List<TaskEntity> tasks = taskRepository.findAll();
        List<TaskResponseDto> taskResponseDtos =tasks.stream().map(taskMapper::mapToRespDto).toList();
        log.info("ActionLog.getAllTasks.end");
        return taskResponseDtos;
    }
    public TaskResponseDto getTaskById(Long id){
        log.info("ActionLog.getTaskById.start taskId {}",id);
        TaskEntity task = findTask(id);
        TaskResponseDto taskResponseDto = taskMapper.mapToRespDto(task);
        log.info("ActionLog.getTaskById.end taskId {}",id);
        return taskResponseDto;
    }
    public void saveTask(TaskRequestDto taskRequestDto){
        log.info("ActionLog.saveTask.start task {}",taskRequestDto);
        System.out.println("DENEME");
        TaskEntity task = taskMapper.mapToEntity(taskRequestDto);
        task.setStatus(Status.TO_DO);
        task.setCreatedDate(LocalDateTime.now());
        taskRepository.save(task);
        log.info("ActionLog.saveTask.end task {}",taskRequestDto);
    }
    public void editTask(Long id,TaskRequestDto taskRequestDto){
        log.info("ActionLog.editTask.start taskId {} task {}",id,taskRequestDto);
        TaskEntity task =findTask(id);
        if(taskRequestDto.getDescription()!=null){
            task.setDescription(taskRequestDto.getDescription());
        }
        if(taskRequestDto.getSubject()!=null){
            task.setSubject(taskRequestDto.getSubject());
        }
        if(taskRequestDto.getReporter()!=null){
            task.setReporter(taskRequestDto.getReporter());
        }
        taskRepository.save(task);
        log.info("ActionLog.editTask.end taskId {} task {}",id,taskRequestDto);
    }
    public TaskResponseDto deleteTask(Long id){
        log.info("ActionLog.deleteTask.start taskId {}",id);
        TaskEntity task = findTask(id);
        TaskResponseDto taskResponseDto = taskMapper.mapToRespDto(task);
        taskRepository.deleteById(id);
        log.info("ActionLog.deleteTask.end taskId {}",id);
        return taskResponseDto;
    }
    private TaskEntity findTask(Long id){
        TaskEntity task = taskRepository.findById(id).
                orElseThrow(()->new NotFoundException(
                        Exceptions.TASK_NOT_FOUND.toString(),
                        String.format("Error ActionLog.findTask taskId {%d}",id)
                ));
        return task;
    }

}
