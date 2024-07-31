package com.example.msgivetaskstoassignee.service;

import com.example.msgivetaskstoassignee.dao.entity.TelesaleEntity;
import com.example.msgivetaskstoassignee.dao.repository.TelesaleRepository;
import com.example.msgivetaskstoassignee.exceptions.NotFoundException;
import com.example.msgivetaskstoassignee.mapper.TelesaleMapper;
import com.example.msgivetaskstoassignee.model.TelesaleRequestDto;
import com.example.msgivetaskstoassignee.model.TelesaleResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TelesaleServiceTest {

    @Mock
    TelesaleRepository telesaleRepository;

    @Mock
    TelesaleMapper telesaleMapper;

    @InjectMocks
    TelesaleService telesaleService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTelesales() {

        TelesaleEntity telesaleEntity1 = new TelesaleEntity();
        TelesaleEntity telesaleEntity2 = new TelesaleEntity();
        List<TelesaleEntity> telesaleEntities = Arrays.asList(telesaleEntity1,telesaleEntity2);

        TelesaleResponseDto telesaleResponseDto1 = new TelesaleResponseDto();
        TelesaleResponseDto telesaleResponseDto2 = new TelesaleResponseDto();
        List<TelesaleResponseDto> telesaleResponseDtos = Arrays.asList(telesaleResponseDto1,telesaleResponseDto2);

        when(telesaleRepository.findAll()).thenReturn(telesaleEntities);
        when(telesaleMapper.mapToRespDto(telesaleEntity1)).thenReturn(telesaleResponseDto1);
        when(telesaleMapper.mapToRespDto(telesaleEntity2)).thenReturn(telesaleResponseDto2);

        List<TelesaleResponseDto> result = telesaleService.getAllTelesales();

        assertNotNull(result);
        assertEquals(result,telesaleResponseDtos);

        verify(telesaleRepository,times(1)).findAll();
        verify(telesaleMapper,times(1)).mapToRespDto(telesaleEntity1);
        verify(telesaleMapper,times(1)).mapToRespDto(telesaleEntity2);
    }

    @Test
    void getTelesaleById() {

        Long id = 2L;
        TelesaleEntity telesaleEntity = new TelesaleEntity();
        TelesaleResponseDto telesaleResponseDto = new TelesaleResponseDto();

        when(telesaleRepository.findById(id)).thenReturn(Optional.of(telesaleEntity));
        when(telesaleMapper.mapToRespDto(telesaleEntity)).thenReturn(telesaleResponseDto);

        TelesaleResponseDto result = telesaleService.getTelesaleById(id);

        assertNotNull(result);
        assertEquals(telesaleResponseDto,result);

        verify(telesaleRepository,times(1)).findById(id);
        verify(telesaleMapper,times(1)).mapToRespDto(telesaleEntity);
    }

    @Test
    void getTelesaleById_NotFound(){

        Long id = 1L;

        when(telesaleRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> telesaleService.getTelesaleById(id));

        verify(telesaleRepository,times(1)).findById(id);
        verify(telesaleMapper,times(0)).mapToRespDto(any());
    }

    @Test
    void saveTelesale() {

        TelesaleRequestDto telesaleRequestDto = new TelesaleRequestDto();
        TelesaleEntity telesaleEntity = new TelesaleEntity();

        when(telesaleMapper.mapToEntity(telesaleRequestDto)).thenReturn(telesaleEntity);
//        when(telesaleRepository.save(telesaleEntity));

        telesaleService.saveTelesale(telesaleRequestDto);

        verify(telesaleMapper,times(1)).mapToEntity(telesaleRequestDto);
        verify(telesaleRepository,times(1)).save(telesaleEntity);
    }

    @Test
    void editTelesale() {

        Long id = 1L;
        TelesaleRequestDto changedTelesale = new TelesaleRequestDto(2L,"telesale");
        TelesaleEntity telesaleEntity = new TelesaleEntity();

        when(telesaleRepository.findById(id)).thenReturn(Optional.of(telesaleEntity));

        telesaleService.editTelesale(id,changedTelesale);

        assertEquals("telesale",telesaleEntity.getName());

        verify(telesaleRepository,times(1)).findById(id);
        verify(telesaleRepository,times(1)).save(telesaleEntity);
    }

    @Test
    void editTelesale_NotFoundCase() {

        Long id = 2L;

        when(telesaleRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> telesaleService.editTelesale(id,any()));
        assertEquals("TELESALE_NOT_FOUND",exception.getErrorMessage());

        verify(telesaleRepository,times(1)).findById(id);
        verify(telesaleRepository,times(0)).save(any());
    }

    @Test
    void deleteTelesale() {

        Long id = 1L;
        TelesaleEntity telesaleEntity = new TelesaleEntity();
        TelesaleRequestDto telesaleRequestDto = new TelesaleRequestDto();

        when(telesaleRepository.findById(id)).thenReturn(Optional.of(telesaleEntity));
        when(telesaleMapper.mapToReqDto(telesaleEntity)).thenReturn(telesaleRequestDto);

        TelesaleRequestDto result = telesaleService.deleteTelesale(id);

        assertEquals(telesaleRequestDto,result);

        verify(telesaleRepository,times(1)).findById(id);
        verify(telesaleMapper,times(1)).mapToReqDto(telesaleEntity);
        verify(telesaleRepository,times(1)).deleteById(id);

    }
}