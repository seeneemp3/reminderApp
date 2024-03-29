package com.personal.ReminderApp.repository;


import com.personal.ReminderApp.model.Reminder;
import com.personal.ReminderApp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    @Query("SELECT r FROM Reminder r WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%')) AND r.user = :user")
    Optional<Page<Reminder>> findByTitleContaining(String title, User user, Pageable pageable);

    @Query("SELECT r FROM Reminder r WHERE LOWER(r.description) LIKE LOWER(CONCAT('%', :description, '%')) AND r.user = :user")
    Optional<Page<Reminder>> findByDescriptionContaining(String description, User user, Pageable pageable);

    Page<Reminder> findAllByUserId(Long userId, Pageable pageable);
}