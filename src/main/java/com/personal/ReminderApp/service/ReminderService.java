package com.personal.ReminderApp.service;

import com.personal.ReminderApp.exception.ReminderNotFoundException;
import com.personal.ReminderApp.model.Reminder;
import com.personal.ReminderApp.model.ReminderDto;
import com.personal.ReminderApp.model.User;
import com.personal.ReminderApp.repository.ReminderRepository;
import com.personal.ReminderApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserService userService;

    public Reminder createReminder(ReminderDto reminderDTO, String username) {
        Reminder reminder = new Reminder();
        reminder.setTitle(reminderDTO.getTitle());
        reminder.setDescription(reminderDTO.getDescription());
        reminder.setRemind(reminderDTO.getRemind());

        User user = userService.getByUsername(username);
        reminder.setUser(user);

        return reminderRepository.save(reminder);
    }

    public Reminder getReminderById(Long id) {
        return reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException("Reminder not found with id: " + id));
    }

    public List<Reminder> getAllReminders(String username) {
        User user = userService.getByUsername(username);
        return reminderRepository.findAllById(Collections.singleton(user.getId()));
    }

    public Reminder updateReminder(Long id, ReminderDto reminderDTO, String username) {
        User user = userService.getByUsername(username);
        if (user.getReminders().stream().anyMatch(reminder -> reminder.getId().equals(id))){

            Reminder reminder = getReminderById(id);
            if (reminderDTO.getTitle() != null && !reminderDTO.getTitle().isEmpty()) {
                reminder.setTitle(reminderDTO.getTitle());
            }
            if (reminderDTO.getDescription() != null && !reminderDTO.getDescription().isEmpty()) {
                reminder.setDescription(reminderDTO.getDescription());
            }
            if (reminderDTO.getRemind() != null) {
                reminder.setRemind(reminderDTO.getRemind());
            }
            return reminderRepository.save(reminder);

        }else throw new ReminderNotFoundException("Unable to update remind");
    }

    public void deleteReminder(Long id) {
        Reminder reminder = getReminderById(id);
        reminderRepository.delete(reminder);
    }
}
