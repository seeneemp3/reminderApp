package com.personal.ReminderApp.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/cookie")
    public ResponseEntity<String> getUserInfo(@AuthenticationPrincipal OAuth2User principal,
                                              @RegisteredOAuth2AuthorizedClient("github") OAuth2AuthorizedClient authorizedClient,
                                              HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String jSessionId = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    jSessionId = cookie.getValue();
                    System.out.println("JSESSIONID: " + jSessionId);
                }
            }
        }
        String token = authorizedClient.getAccessToken().getTokenValue();

        return ResponseEntity.ok(
                "User: " + principal.getAttribute("login") + "\n" +
                        "JSessionID: " + jSessionId
        );
    }
}
