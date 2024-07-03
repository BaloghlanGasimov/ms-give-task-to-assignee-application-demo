package com.example.msgivetaskstoassignee.controller;

import com.example.msgivetaskstoassignee.model.TelesaleRequestDto;
import com.example.msgivetaskstoassignee.model.TelesaleResponseDto;
import com.example.msgivetaskstoassignee.service.TelesaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public void saveTelesale(@RequestBody @Valid TelesaleRequestDto telesaleReqDto){
        telesaleService.saveTelesale(telesaleReqDto);
    }
    @PutMapping("/{id}")
    public void editTelesale(@PathVariable Long id,@RequestBody TelesaleRequestDto telesaleReqDto){
        telesaleService.editTelesale(id, telesaleReqDto);
    }
    @DeleteMapping("/{id}")
    public TelesaleRequestDto deleteTelesale(@PathVariable Long id){
        return telesaleService.deleteTelesale(id);
    }
}
