package com.personal.ReminderApp;

import com.personal.ReminderApp.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ReminderAppApplication {

    @Autowired
    private SchedulerService scheduler;

    public static void main(String[] args) {
        SpringApplication.run(ReminderAppApplication.class, args);
    }

    @EventListener(ContextRefreshedEvent.class)
    public void scheduleRemindersOnStartup() {
        scheduler.scheduleAllReminders();
    }

}
