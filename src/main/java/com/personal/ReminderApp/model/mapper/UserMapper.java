package com.personal.ReminderApp.model.mapper;


import com.personal.ReminderApp.model.User;
import com.personal.ReminderApp.model.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toDto(User user);

}
