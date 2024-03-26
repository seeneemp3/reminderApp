package com.personal.ReminderApp.service;

import com.personal.ReminderApp.exception.UserNotFoundException;
import com.personal.ReminderApp.model.User;
import com.personal.ReminderApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User getByUsername(String username){
        return repository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(""));
    }

    public User saveUser(OAuth2User oAuth2User) {
        String username = oAuth2User.getAttribute("login");
        Optional<User> existingUser = repository.findByUsername(username);

        if (existingUser.isPresent()) {
            return existingUser.get();
        } else {
            User newUser = new User();
            newUser.setUsername(username);

            return repository.save(newUser);
        }
    }

    public String userInfo(String username){

        String info = "";
        Optional<User> existingUser = repository.findByUsername(username);
        if (existingUser.isPresent()) {
            info = "Username: " + existingUser.get().getUsername();
            return info;
        }
        return info;
    }


}
