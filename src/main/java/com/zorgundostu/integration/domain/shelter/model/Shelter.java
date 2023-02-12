package com.zorgundostu.integration.domain.shelter.model;

import com.zorgundostu.integration.domain.identity.model.BaseEntity;

public class Shelter extends BaseEntity {
    private String id;
    private String tcKimlik;
    private String ad;
    private String soyad;
    private String telefon;
    private String eposta;
    private String il;
    private String ilce;
    private String adres;
    private String oda;
    private int enfazlaKisi;
    private boolean esyali;
    private String musaitlikTarihi;
    private int musaitlikSuresiGun;
    private String aciklama;
    private String basvuruYapanKurum;
}
