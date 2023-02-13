package com.canevim.integration.domain.notification.model.notification;

public enum NotificationContent {
    WELCOME("CANEVIM'e hoşgeldiniz", "Welcome to CANEVIM"),
    CONFIRMATION("Hesabınızı onaylayın", "Confirm your account"),
    PASSWORD_RESET("Şifrenizi sıfırlayın", "Reset your password"),
    PASSWORD_CHANGED("Şifreniz değiştirildi", "Your password has been changed"),
    OTHER("Diğer", "Other");


    private String tr;
    private String en;

    NotificationContent(String tr, String en) {
        this.tr = tr;
        this.en = en;
    }

    public String getTr() {
        return this.tr;
    }

    public String getEn() {
        return this.en;
    }

    public String get(String language) {
        if (language.equals("tr")) {
            return this.tr;
        } else if (language.equals("en")) {
            return this.en;
        } else {
            return "Language not supported: " + language;
        }
    }
}
