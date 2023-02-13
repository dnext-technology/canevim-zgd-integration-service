package com.canevim.integration.domain.notification.util.mapper;

import com.canevim.integration.domain.notification.model.notification.Notification;
import com.canevim.integration.domain.notification.model.notification.NotificationDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification toDao(NotificationDto notificationDto);

    NotificationDto toDto(Notification requester);
}
