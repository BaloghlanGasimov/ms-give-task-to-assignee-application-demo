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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class TelesaleService {
    private final TaskRepository taskRepository;
    private final TelesaleRepository telesaleRepository;
    private final TelesaleMapper telesaleMapper;
    private final ImageService imageService;

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

    public void saveTelesale(TelesaleRequestDto telesaleReqDto, MultipartFile image) {
        log.info("ActionLog.saveTelesale.start telesale {}", telesaleReqDto);
        TelesaleEntity telesale = telesaleMapper.mapToEntity(telesaleReqDto);
        try {
            InputStream imageStream = image.getInputStream();
            String filePath = imageService.uploadAndGetPathImage("telesales",image.getOriginalFilename(),imageStream);
            telesale.setIdCardImage(filePath);
            telesaleRepository.save(telesale);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("ActionLog.saveTelesale.end telesale {}", telesaleReqDto);
    }

    public void editTelesale(Long id, TelesaleRequestDto telesaleReqDto,MultipartFile image){
        log.info("ActionLog.editTelesale.start telesaleId {} telesale {}",id, telesaleReqDto);
        TelesaleEntity telesale = findTelesale(id);
        if(telesaleReqDto.getName()!=null){
            telesale.setName(telesaleReqDto.getName());
        }
        if(telesaleReqDto.getMail()!=null){
            telesale.setMail(telesaleReqDto.getMail());
        }
        try {
            if(image!=null){
                if (telesale.getIdCardImage() != null) {
                    InputStream imageStream = null;

                        imageStream = image.getInputStream();

                    imageService.deleteImage("telesales",findImageName(telesale.getIdCardImage()));
                    String newImagePath = imageService.uploadAndGetPathImage("telesales",image.getOriginalFilename(),imageStream);
                    telesale.setIdCardImage(newImagePath);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        telesaleRepository.save(telesale);
        log.info("ActionLog.editTelesale.end telesaleId {} telesale {}",id, telesaleReqDto);
    }
    private String findImageName(String imagePath){
        String fPath = imagePath.split("\\?")[0];
        return fPath.split("telesales/")[1];

    }

    @Transactional
    public TelesaleRequestDto deleteTelesale(Long id){
        log.info("ActionLog.deleteTelesale.start telesaleId {}",id);
        TelesaleEntity telesale = findTelesale(id);
        TelesaleRequestDto telesaleReqDto = telesaleMapper.mapToReqDto(telesale);
        telesaleRepository.deleteById(id);
        imageService.deleteImage("telesales",findImageName(telesale.getIdCardImage()));
        log.info("ActionLog.deleteTelesale.end telesaleId {}",id);
        return telesaleReqDto;
    }

    private TelesaleEntity findTelesale(Long id) {
        TelesaleEntity telesale = telesaleRepository.findById(id).
                orElseThrow(() -> new NotFoundException(
                        Exceptions.TELESALE_NOT_FOUND.toString(),
                        String.format("Error ActionLog.findTelesale telesaleId {%d}", id)
                ));
        return telesale;
    }
}
