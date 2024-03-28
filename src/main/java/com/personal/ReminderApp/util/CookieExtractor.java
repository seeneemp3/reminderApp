package com.personal.ReminderApp.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieExtractor {

    public static String extractFromRequest(HttpServletRequest request) {

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
        return jSessionId;
    }
}
