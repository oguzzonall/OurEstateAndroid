package com.oguzzonall.e_emlakapp.Models;

public class RegisterPojo {
    private boolean Sonuc;
    private String Mesaj;

    public boolean isSonuc() {
        return Sonuc;
    }

    public void setSonuc(boolean sonuc) {
        Sonuc = sonuc;
    }

    public String getMesaj() {
        return Mesaj;
    }

    public void setMesaj(String mesaj) {
        Mesaj = mesaj;
    }

    @Override
    public String toString() {
        return "RegisterPojo{" +
                "Sonuc=" + Sonuc +
                ", Mesaj='" + Mesaj + '\'' +
                '}';
    }
}
