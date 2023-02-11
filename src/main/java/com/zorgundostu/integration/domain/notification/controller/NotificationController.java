package com.zorgundostu.integration.domain.notification.controller;

import com.zorgundostu.integration.domain.notification.model.notification.NotificationDto;
import com.zorgundostu.integration.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationController implements NotificationApi {

    private final NotificationService notificationService;

    @Override
    public ResponseEntity<Object> createNotification(Map<String, String> header, NotificationDto dto) {
        return ResponseEntity.ok(notificationService.createNotification(dto));
    }

    @Override
    public ResponseEntity<Object> getAllNotifications(Map<String, String> header, Integer page, Integer size) {
        return ResponseEntity.ok(notificationService.getAllActiveNotifications(page, size));
    }
}
