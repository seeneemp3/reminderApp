package com.personal.ReminderApp.controller;

import com.personal.ReminderApp.util.CookieExtractor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/cookie")
    public ResponseEntity<String> getUserInfo(@AuthenticationPrincipal OAuth2User principal,
                                              @RegisteredOAuth2AuthorizedClient("github") OAuth2AuthorizedClient authorizedClient,
                                              HttpServletRequest request) {
        String jSessionId = CookieExtractor.extractFromRequest(request);
        return ResponseEntity.ok(
                "User: " + principal.getAttribute("login") + "\n" +
                        "JSessionID: " + jSessionId
        );
    }
}
