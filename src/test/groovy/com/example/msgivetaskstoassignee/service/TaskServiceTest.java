package com.example.msgivetaskstoassignee.service;

import com.example.msgivetaskstoassignee.dao.entity.TaskEntity;
import com.example.msgivetaskstoassignee.dao.entity.TelesaleEntity;
import com.example.msgivetaskstoassignee.dao.repository.TaskRepository;
import com.example.msgivetaskstoassignee.dao.repository.TelesaleRepository;
import com.example.msgivetaskstoassignee.enums.Status;
import com.example.msgivetaskstoassignee.exceptions.NotFoundException;
import com.example.msgivetaskstoassignee.mapper.TaskMapper;
import com.example.msgivetaskstoassignee.model.TaskRequestDto;
import com.example.msgivetaskstoassignee.model.TaskResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {
    @Mock
    TelesaleRepository telesaleRepository;

    @Mock
    TaskRepository taskRepository;

    @Mock
    TaskMapper taskMapper;

    @InjectMocks
    TaskService taskService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllTasks_Success() {
        TaskEntity taskEntity1 = new TaskEntity();
        TaskEntity taskEntity2 = new TaskEntity();
        List<TaskEntity> taskEntities = Arrays.asList(taskEntity1,taskEntity2);

        TaskResponseDto taskResponseDto1 = new TaskResponseDto();
        TaskResponseDto taskResponseDto2 = new TaskResponseDto();
        List<TaskResponseDto> taskResponseDtos = Arrays.asList(taskResponseDto1,taskResponseDto2);

        when(taskRepository.findAll()).thenReturn(taskEntities);
        when(taskMapper.mapToRespDto(taskEntity1)).thenReturn(taskResponseDto1);
        when(taskMapper.mapToRespDto(taskEntity2)).thenReturn(taskResponseDto2);

        List<TaskResponseDto> result = taskService.getAllTasks();

        assertEquals(taskResponseDtos,result);

        verify(taskRepository,times(1)).findAll();
        verify(taskMapper,times(1)).mapToRespDto(taskEntity1);
        verify(taskMapper,times(1)).mapToRespDto(taskEntity2);

    }

    @Test
    void getTaskById_Success() {
        Long id =1L;
        TaskEntity taskEntity = new TaskEntity();
        TaskResponseDto taskResponseDto = new TaskResponseDto();

        when(taskRepository.findById(id)).thenReturn(Optional.of(taskEntity));
        when(taskMapper.mapToRespDto(taskEntity)).thenReturn(taskResponseDto);

        TaskResponseDto result = taskService.getTaskById(id);

        assertEquals(taskResponseDto,result);

        verify(taskRepository,times(1)).findById(id);
        verify(taskMapper,times(1)).mapToRespDto(taskEntity);
    }

    @Test
    void getTaskById_NotFound_Success(){

        Long id = 1L;

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> taskService.getTaskById(id));
        assertEquals("TASK_NOT_FOUND",exception.getErrorMessage());

        verify(taskRepository,times(1)).findById(id);
        verify(taskMapper,times(0)).mapToRespDto(any());
    }

    @Test
    void saveTask_Success() {

        TaskRequestDto savedTaskRequestDto = new TaskRequestDto("subject","description","reporter",10L,48);
        TaskEntity taskEntity = new TaskEntity(1L,"subject","reporter","description",LocalDateTime.now(), LocalDateTime.of(2024,10,11,12,46), Status.TO_DO,48,null);
        TelesaleEntity telesaleEntity = new TelesaleEntity();

        when(taskMapper.mapToEntity(savedTaskRequestDto)).thenReturn(taskEntity);
        when(telesaleRepository.findById(savedTaskRequestDto.getTelesaleId())).thenReturn(Optional.of(telesaleEntity));

        LocalDateTime currentTime = LocalDateTime.now();
        taskEntity.setTelesale(telesaleEntity);
        taskEntity.setStatus(Status.TO_DO);
        taskEntity.setCreatedDate(currentTime);

        assertEquals(telesaleEntity,taskEntity.getTelesale());
        assertEquals("TO_DO",Status.TO_DO.toString());
        assertEquals(currentTime,taskEntity.getCreatedDate());

        verify(taskMapper,times(1)).mapToEntity(savedTaskRequestDto);
        verify(telesaleRepository,times(1)).findById(savedTaskRequestDto.getTelesaleId());
        verify(taskRepository,times(1)).save(taskEntity);
    }

    @Test
    void editTask_Success() {

        Long id = 1L;
        TaskRequestDto changedTaskRequestDto = new TaskRequestDto();
        TaskEntity taskEntity = new TaskEntity(1L,"subject","reporter","description",LocalDateTime.now(), LocalDateTime.of(2024,10,11,12,46), Status.TO_DO,48,null);

        when(taskRepository.findById(id)).thenReturn(Optional.of(taskEntity));

        taskService.editTask(id,changedTaskRequestDto);

        assertEquals("subject",taskEntity.getSubject());
        assertEquals("reporter",taskEntity.getReporter());
        assertEquals("description",taskEntity.getDescription());

        verify(taskRepository,times(1)).findById(id);
        verify(taskRepository,times(1)).save(taskEntity);
    }

    @Test
    void editTask_NotFound() {

        Long id = 1L;
        TaskRequestDto changedTaskRequestDto = new TaskRequestDto();

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,() -> taskService.editTask(id,changedTaskRequestDto));
        assertEquals("TASK_NOT_FOUND",exception.getErrorMessage());

        verify(taskRepository,times(1)).findById(id);
        verify(taskRepository,times(0)).save(any());
    }
    @Test
    void deleteTask_Success() {

        Long id = 1L;
        TaskEntity taskEntity = new TaskEntity();
        TaskResponseDto taskResponseDto = new TaskResponseDto();

        when(taskRepository.findById(id)).thenReturn(Optional.of(taskEntity));
        when(taskMapper.mapToRespDto(taskEntity)).thenReturn(taskResponseDto);

        TaskResponseDto result = taskService.deleteTask(id);

        assertEquals(taskResponseDto,result);

        verify(taskRepository,times(1)).findById(id);
        verify(taskMapper,times(1)).mapToRespDto(taskEntity);
        verify(taskRepository,times(1)).deleteById(id);
    }

    @Test
    void deleteTask_NotFound() {

        Long id = 1L;

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,()->taskService.deleteTask(id));
        assertEquals("TASK_NOT_FOUND",exception.getErrorMessage());

        verify(taskRepository,times(1)).findById(id);
        verify(taskMapper,times(0)).mapToRespDto(any());
        verify(taskRepository,times(0)).deleteById(id);
    }

}