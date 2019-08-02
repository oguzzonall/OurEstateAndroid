package com.oguzzonall.e_emlakapp.Models;

public class LoginPojo {
    private boolean Sonuc;
    private int KisiId;
    private String Mesaj;
    private String Kad;

    public boolean isSonuc() {
        return Sonuc;
    }

    public void setSonuc(boolean sonuc) {
        Sonuc = sonuc;
    }

    public int getKisiId() {
        return KisiId;
    }

    public void setKisiId(int kisiId) {
        KisiId = kisiId;
    }

    public String getMesaj() {
        return Mesaj;
    }

    public void setMesaj(String mesaj) {
        Mesaj = mesaj;
    }

    public String getKad() {
        return Kad;
    }

    public void setKad(String kad) {
        Kad = kad;
    }

    @Override
    public String toString() {
        return "LoginPojo{" +
                "Sonuc=" + Sonuc +
                ", KisiId=" + KisiId +
                ", Mesaj='" + Mesaj + '\'' +
                ", Kad='" + Kad + '\'' +
                '}';
    }
}
