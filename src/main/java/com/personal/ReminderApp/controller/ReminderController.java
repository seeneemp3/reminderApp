package com.personal.ReminderApp.controller;

import com.personal.ReminderApp.model.Reminder;
import com.personal.ReminderApp.model.dto.ReminderDto;
import com.personal.ReminderApp.model.mapper.ReminderMapper;
import com.personal.ReminderApp.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reminder")
public class ReminderController {
    private final ReminderService service;
    private final ReminderMapper mapper;

    @PostMapping
    public ResponseEntity<ReminderDto> create(@RequestBody ReminderDto reminder,
                                              @AuthenticationPrincipal OAuth2User principal) {
        Reminder rem = service.createReminder(reminder, principal.getAttribute("login"));
        return ResponseEntity.ok(mapper.toDto(rem));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ReminderDto>> getAll(
            @AuthenticationPrincipal OAuth2User principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sort)));
        Page<Reminder> remindersPage = service.getAllReminders(principal.getAttribute("login"), pageable);
        Page<ReminderDto> dtoPage = remindersPage.map(mapper::toDto);
        return ResponseEntity.ok(dtoPage);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ReminderDto> update(@PathVariable Long id,
                                              @RequestBody ReminderDto reminder,
                                              @AuthenticationPrincipal OAuth2User principal) {
        Reminder rem = service.updateReminder(id, reminder, principal.getAttribute("login"));
        return ResponseEntity.ok(mapper.toDto(rem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal OAuth2User principal) {
        service.deleteReminder(id, principal.getAttribute("login"));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ReminderDto>> search(
            @AuthenticationPrincipal OAuth2User principal,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        String username = principal.getAttribute("login");
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sort)));
        Page<Reminder> remindersPage;

        if (title != null && !title.isEmpty()) {
            remindersPage = service.searchByTitle(title, username, pageable);
        } else if (description != null && !description.isEmpty()) {
            remindersPage = service.searchByDescription(description, username, pageable);
        } else {
            remindersPage = service.getAllReminders(username, pageable);
        }

        Page<ReminderDto> dtoPage = remindersPage.map(mapper::toDto);
        return ResponseEntity.ok(dtoPage);
    }

    private List<Sort.Order> createSortOrder(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();

        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] sortString = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(sortString[1]), sortString[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        return orders;
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }
}
