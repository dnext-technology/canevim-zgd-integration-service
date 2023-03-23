package com.canevim.integration.domain.notification.model.notification;

import com.vladmihalcea.hibernate.type.json.JsonType;
import com.canevim.integration.domain.notification.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;

@Data
@Entity(name = "Notification")
@Table(name = "notification")
public class Notification extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    private NotificationContent content;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<String> gsmList;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<NotificationParameter> parameterList;
}