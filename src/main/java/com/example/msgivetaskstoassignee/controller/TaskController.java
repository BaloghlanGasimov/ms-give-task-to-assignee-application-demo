package com.example.msgivetaskstoassignee.controller;

import com.example.msgivetaskstoassignee.model.TaskRequestDto;
import com.example.msgivetaskstoassignee.model.TaskResponseDto;
import com.example.msgivetaskstoassignee.service.TaskService;
import com.fasterxml.jackson.databind.node.LongNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    @GetMapping
    public List<TaskResponseDto> getAllTasks(){
        return taskService.getAllTasks();
    }
    @GetMapping("/{id}")
    public TaskResponseDto getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }
    @PostMapping
    public void saveTask(@RequestBody @Valid TaskRequestDto taskRequestDto){
        taskService.saveTask(taskRequestDto);
    }
    @PutMapping("/{id}")
    public void editTask(@PathVariable Long id,@RequestBody TaskRequestDto taskRequestDto){
        taskService.editTask(id,taskRequestDto);
    }
    @DeleteMapping("/{id}")
    public TaskResponseDto deleteTask(@PathVariable Long id){
        return taskService.deleteTask(id);
    }
}
