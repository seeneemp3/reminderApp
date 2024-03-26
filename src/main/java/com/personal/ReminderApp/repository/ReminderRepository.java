package com.personal.ReminderApp.repository;


import com.personal.ReminderApp.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    @Query("SELECT r FROM Reminder r WHERE r.description LIKE %:description%")
    Optional<List<Reminder>> findByDescriptionContaining(String description);

    @Query("SELECT r FROM Reminder r WHERE r.title LIKE %:title%")
    Optional<List<Reminder>> findByTitleContaining(String title);

}
