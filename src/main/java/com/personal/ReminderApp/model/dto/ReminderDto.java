package com.personal.ReminderApp.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReminderDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime remind;
    private Long userId;

}
