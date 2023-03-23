package com.canevim.integration.domain.notification.repository;


import com.canevim.integration.domain.notification.model.notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    Page<Notification> findAll(Pageable pageable);
}
