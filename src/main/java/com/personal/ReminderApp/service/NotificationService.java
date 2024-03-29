package com.personal.ReminderApp.service;

import com.personal.ReminderApp.model.Reminder;

public interface NotificationService {
    void notify(Reminder reminder);
}