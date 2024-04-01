package com.personal.ReminderApp.service;

import com.personal.ReminderApp.model.Reminder;
import com.personal.ReminderApp.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final Scheduler scheduler;
    private final ReminderRepository repository;

    public void scheduleAllReminders() {
        List<Reminder> list = repository.findAll();
        list.forEach(this::scheduleSingleReminder);
    }

    public void scheduleSingleReminder(Reminder reminder) {
        JobDetail jobDetail = JobBuilder.newJob(TerminalNotificationService.class)
                .withIdentity("reminderJob-" + reminder.getId())
                .usingJobData("reminderId", reminder.getId())
                .build();

        LocalDateTime triggerDateTime = reminder.getRemind();
        Date triggerDate = Date.from(triggerDateTime
                .atZone(TimeZone.getTimeZone("Europe/Moscow").toZoneId())
                .toInstant());
        log.info("Job " + reminder.getTitle() + " will start at:" + triggerDate);

        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("reminderTrigger-" + reminder.getId())
                .startAt(triggerDate)
                .build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("Failed to schedule job for reminder: {}", reminder, e);
        }
    }

    public void deleteReminder(Long reminderId) {
        try {
            JobKey jobKey = new JobKey("reminderJob-" + reminderId);
            TriggerKey triggerKey = new TriggerKey("reminderTrigger-" + reminderId);

            scheduler.unscheduleJob(triggerKey);

            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
