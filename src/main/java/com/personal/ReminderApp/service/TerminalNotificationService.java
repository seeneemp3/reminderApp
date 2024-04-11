package com.personal.ReminderApp.service;

import com.personal.ReminderApp.exception.ReminderNotFoundException;
import com.personal.ReminderApp.model.Reminder;
import com.personal.ReminderApp.model.User;
import com.personal.ReminderApp.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TerminalNotificationService implements NotificationService, Job {

    private final ReminderRepository repository;

    @Override
    public void notify(Reminder reminder) {
        User user = reminder.getUser();
        log.info("\n----- To: " + user.getUsername() + " -----\n" + reminder.getTitle());
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        Long reminderId = dataMap.getLong("reminderId");
        Reminder reminder = repository.findById(reminderId)
                .orElseThrow(() -> new ReminderNotFoundException("Reminder not found with id: " + reminderId));
        notify(reminder);
    }

}
