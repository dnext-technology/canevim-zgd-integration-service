package com.zorgundostu.integration.domain.notification.model.notification;

import lombok.Data;

import java.util.List;

@Data
public class Sms {
    private String username;
    private String password;
    private String vendor;
    private String header;
    private String message;
    private List<String> gsmList;
    private String charset;
    private String startdate;
    private String exdate;
}
