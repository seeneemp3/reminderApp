package com.personal.ReminderApp.controller;

import com.personal.ReminderApp.repository.UserRepository;
import com.personal.ReminderApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<String> info(@AuthenticationPrincipal OAuth2User principal){
        return ResponseEntity.ok(service.userInfo(principal.getAttribute("login")));
    }

}
