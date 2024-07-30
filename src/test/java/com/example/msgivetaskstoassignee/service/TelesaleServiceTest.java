package com.example.msgivetaskstoassignee.service;

import com.example.msgivetaskstoassignee.dao.entity.TelesaleEntity;
import com.example.msgivetaskstoassignee.dao.repository.TelesaleRepository;
import com.example.msgivetaskstoassignee.mapper.TelesaleMapper;
import com.example.msgivetaskstoassignee.model.TelesaleResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
statuy
class TelesaleServiceTest {

    @Mock
    private TelesaleRepository telesaleRepository;

    @Mock
    private TelesaleMapper telesaleMapper;

    @InjectMocks
    private TelesaleService telesaleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTelesales() {
        TelesaleEntity telesaleEntity1 = new TelesaleEntity();
        TelesaleEntity telesaleEntity2 = new TelesaleEntity();
        List<TelesaleEntity> telesales = Arrays.asList(telesaleEntity1, telesaleEntity2);

        TelesaleResponseDto telesaleResponseDto1 = new TelesaleResponseDto();
        TelesaleResponseDto telesaleResponseDto2 = new TelesaleResponseDto();
        List<TelesaleResponseDto> telesaleResponseDtos = Arrays.asList(telesaleResponseDto1, telesaleResponseDto2);

        when(telesaleRepository.findAll()).thenReturn(telesales);
        when(telesaleMapper.mapToRespDto(telesaleEntity1)).thenReturn(telesaleResponseDto1);
        when(telesaleMapper.mapToRespDto(telesaleEntity2)).thenReturn(telesaleResponseDto2);


        List<TelesaleResponseDto> result = telesaleService.getAllTelesales();

        assertEquals(telesaleResponseDtos, result);
    }

    @Test
    void getTelesaleById() {
    }

    @Test
    void saveTelesale() {
    }

    @Test
    void editTelesale() {
    }

    @Test
    void deleteTelesale() {
    }
}