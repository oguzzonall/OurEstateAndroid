package com.oguzzonall.e_emlakapp.Models;

public class IlanBegeniPojo {
    private int BegenmeSayisi;
    private boolean Begeni;
    private boolean Sonuc;
    private String Mesaj;

    public int getBegenmeSayisi() {
        return BegenmeSayisi;
    }

    public void setBegenmeSayisi(int begenmeSayisi) {
        BegenmeSayisi = begenmeSayisi;
    }

    public boolean isBegeni() {
        return Begeni;
    }

    public void setBegeni(boolean begeni) {
        Begeni = begeni;
    }

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
        return "IlanBegeniPojo{" +
                "BegenmeSayisi=" + BegenmeSayisi +
                ", Begeni=" + Begeni +
                ", Sonuc=" + Sonuc +
                ", Mesaj='" + Mesaj + '\'' +
                '}';
    }
}
