package com.personal.ReminderApp.controller;

import com.personal.ReminderApp.model.Reminder;
import com.personal.ReminderApp.model.ReminderDto;
import com.personal.ReminderApp.model.ReminderMapper;
import com.personal.ReminderApp.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reminder")
public class ReminderController {
    private final ReminderService service;
    private final ReminderMapper mapper;

    @PostMapping
    public ResponseEntity<ReminderDto> create(@RequestBody ReminderDto reminder,
                                              @AuthenticationPrincipal OAuth2User principal){
        Reminder rem = service.createReminder(reminder, principal.getAttribute("login"));
        return ResponseEntity.ok(mapper.toDto(rem));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReminderDto>> getAll(@AuthenticationPrincipal OAuth2User principal){
        List<Reminder> list = service.getAllReminders(principal.getAttribute("login"));
        return ResponseEntity.ok(mapper.toDto(list));
    }

    @PatchMapping("{id}")
    public ResponseEntity<ReminderDto> update(@PathVariable Long id,
                                              @RequestBody ReminderDto reminder,
                                              @AuthenticationPrincipal OAuth2User principal){
        Reminder rem = service.updateReminder(id, reminder, principal.getAttribute("login"));
        return ResponseEntity.ok(mapper.toDto(rem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteReminder(id);
        return ResponseEntity.noContent().build();
    }

}
