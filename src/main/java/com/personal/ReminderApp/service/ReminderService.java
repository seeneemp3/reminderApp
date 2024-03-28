package com.personal.ReminderApp.service;

import com.personal.ReminderApp.exception.ReminderNotFoundException;
import com.personal.ReminderApp.model.Reminder;
import com.personal.ReminderApp.model.User;
import com.personal.ReminderApp.model.dto.ReminderDto;
import com.personal.ReminderApp.model.mapper.ReminderMapper;
import com.personal.ReminderApp.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserService userService;
    private final ReminderMapper mapper;

    public Reminder createReminder(ReminderDto reminderDTO, String username) {

        Reminder reminder = mapper.toEntity(reminderDTO);
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
        return reminderRepository.findAllByUserId(user.getId());
    }

    public Reminder updateReminder(Long id, ReminderDto reminderDTO, String username) {
        User user = userService.getByUsername(username);
        if (user.getReminders().stream().anyMatch(reminder -> reminder.getId().equals(id))) {

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

        } else throw new ReminderNotFoundException("Unable to update remind");
    }

    public void deleteReminder(Long id, String username) {
        User user = userService.getByUsername(username);
        Reminder reminder = getReminderById(id);
        if (Objects.equals(reminder.getUser().getId(), user.getId())) {
            reminderRepository.delete(reminder);
        }
    }

    public List<Reminder> searchByTitle(String title, String username) {
        User user = userService.getByUsername(username);
        return reminderRepository.findByTitleContaining(title, user)
                .orElseThrow(() -> new ReminderNotFoundException("Reminder not found"));
    }

    public List<Reminder> searchByDescription(String description, String username) {
        User user = userService.getByUsername(username);
        return reminderRepository.findByDescriptionContaining(description, user)
                .orElseThrow(() -> new ReminderNotFoundException("Reminder not found"));
    }
}
