package com.example.msgivetaskstoassignee.schedules;

import com.example.msgivetaskstoassignee.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class CheckTaskExpireDateSchedule {
    private final TaskService taskService;
    @Scheduled(cron = "0 1 * * * *")
    public void checkExpireDate(){
        log.info("ActionLog.checkExpireDate.start");
        System.out.println("it's working");
        taskService.checkExpireDate();


        log.info("ActionLog.checkExpireDate.end");

    }

}
