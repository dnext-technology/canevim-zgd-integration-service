package com.zorgundostu.integration.domain.shelter.model;

public record ShelterDto(
        String id,
        String tcKimlik,
        String ad,
        String soyad,
        String telefon,
        String eposta,
        String il,
        String ilce,
        String adres,
        String oda,
        int enfazlaKisi,
        boolean esyali,
        String musaitlikTarihi,
        int musaitlikSuresiGun,
        String aciklama,
        String basvuruYapanKurum

) {
}
