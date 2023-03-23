package com.canevim.integration.domain.notification.service;

import com.canevim.integration.domain.notification.client.SmsClient;
import com.canevim.integration.domain.notification.model.notification.NotificationContent;
import com.canevim.integration.domain.notification.model.notification.NotificationDto;
import com.canevim.integration.domain.notification.model.notification.NotificationType;
import com.canevim.integration.domain.notification.repository.NotificationRepository;
import com.canevim.integration.domain.notification.util.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;

    private final SmsClient smsClient;

    public NotificationDto createNotification(NotificationDto notificationDto) {
        if (notificationDto.type().equals(NotificationType.SMS)) {
            sendSms(notificationDto);
        } else if (notificationDto.type().equals(NotificationType.EMAIL)) {
            sendMail(notificationDto);
        } else {
            throw new IllegalArgumentException("Notification type is not supported");
        }
        return saveNotification(notificationDto);
    }

    private void sendMail(NotificationDto notificationDto) {
    }

    private NotificationDto saveNotification(NotificationDto notificationDto) {
        var notification = notificationMapper.toDao(notificationDto);
        var savedNotification = notificationRepository.save(notification);
        return notificationMapper.toDto(savedNotification);
    }

    private void sendSms(NotificationDto notificationDto) {
        var content = notificationDto.content();
        var message = "";
        if (content.equals(NotificationContent.OTHER)) {
            message = notificationDto.getParameterValueByKey("content");
        } else message = content.getTr();
        
        String response = smsClient.sendSms(notificationDto.gsmList(), message);
        
        log.info("SMS is sent to {}", response);
        notificationDto.addParameter("responseId", response.substring(response.indexOf("ID:") + 3, response.indexOf("</string>")));
    }


    public Page<NotificationDto> getAllActiveNotifications(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdDate");
        return notificationRepository.findAll(pageable).map(notificationMapper::toDto);
    }
}
