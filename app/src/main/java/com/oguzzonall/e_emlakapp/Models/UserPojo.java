package com.oguzzonall.e_emlakapp.Models;

public class UserPojo {
    private String AdSoyad;
    private Boolean Sonuc;

    public String getAdSoyad() {
        return AdSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        AdSoyad = adSoyad;
    }

    public Boolean getSonuc() {
        return Sonuc;
    }

    public void setSonuc(Boolean sonuc) {
        Sonuc = sonuc;
    }

    @Override
    public String toString() {
        return "UserPojo{" +
                "AdSoyad='" + AdSoyad + '\'' +
                ", Sonuc=" + Sonuc +
                '}';
    }
}
