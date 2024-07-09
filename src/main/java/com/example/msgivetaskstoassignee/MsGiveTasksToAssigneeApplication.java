package com.example.msgivetaskstoassignee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MsGiveTasksToAssigneeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsGiveTasksToAssigneeApplication.class, args);
    }

}
