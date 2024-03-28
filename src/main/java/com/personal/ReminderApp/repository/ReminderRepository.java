package com.personal.ReminderApp.repository;


import com.personal.ReminderApp.model.Reminder;
import com.personal.ReminderApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    @Query("SELECT r FROM Reminder r WHERE LOWER(r.description) LIKE LOWER(CONCAT('%', :description, '%')) AND r.user = :user")
    Optional<List<Reminder>> findByDescriptionContaining(String description, User user);

    @Query("SELECT r FROM Reminder r WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%')) AND r.user = :user")
    Optional<List<Reminder>> findByTitleContaining(String title, User user);

    List<Reminder> findAllByUserId(Long userId);

}
