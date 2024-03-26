package com.personal.ReminderApp.model;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReminderMapper {
    @Mapping(source = "user.id", target = "userId")
    ReminderDto toDto(Reminder reminder);

    @Mapping(source = "userId", target = "user.id")
    Reminder toEntity(ReminderDto dto);

    List<ReminderDto> toDto(List<Reminder> reminders);
}
