package com.personal.ReminderApp.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String username;
    private List<ReminderDto> reminders;
}
