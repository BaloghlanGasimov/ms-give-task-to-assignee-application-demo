package com.example.msgivetaskstoassignee.controller;

import com.example.msgivetaskstoassignee.model.TaskResponseDto;
import com.example.msgivetaskstoassignee.model.TelesaleRequestDto;
import com.example.msgivetaskstoassignee.model.TelesaleResponseDto;
import com.example.msgivetaskstoassignee.service.TelesaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/telesales")
public class TelesaleController {
    private final TelesaleService telesaleService;

    @GetMapping
    public List<TelesaleResponseDto> getAllTelesales(){
       return telesaleService.getAllTelesales();
    }

    @GetMapping("/{id}")
    public TelesaleResponseDto getTelesaleById(@PathVariable Long id){
        return telesaleService.getTelesaleById(id);
    }

    @PatchMapping("/{id}/tasks/{taskId}/start")
    public void startTask(@PathVariable Long id,@PathVariable Long taskId){
        telesaleService.startTelesaleTask(id,taskId);
    }

    @PostMapping
    public void saveTelesale(
            @RequestPart(name = "telesaleInfo") @Valid TelesaleRequestDto telesaleReqDto,
            @RequestPart(name = "image") MultipartFile image
    ){
        telesaleService.saveTelesale(telesaleReqDto,image);
    }

    @PutMapping("/{id}")
    public void editTelesale(
            @PathVariable Long id,
            @RequestPart(name = "telesaleInfo") TelesaleRequestDto telesaleReqDto,
            @RequestPart(name = "image") MultipartFile image
    ){
        telesaleService.editTelesale(id, telesaleReqDto,image);
    }

    @DeleteMapping("/{id}")
    public TelesaleRequestDto deleteTelesale(@PathVariable Long id){
        return telesaleService.deleteTelesale(id);
    }
}
