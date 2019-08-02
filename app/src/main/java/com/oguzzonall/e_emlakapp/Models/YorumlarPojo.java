package com.oguzzonall.e_emlakapp.Models;

public class YorumlarPojo {
    private String Icerik;
    private String Baslik;
    private String KullaniciAdi;
    private String Tarih;
    public boolean Sonuc;

    public String getIcerik() {
        return Icerik;
    }

    public void setIcerik(String icerik) {
        Icerik = icerik;
    }

    public String getBaslik() {
        return Baslik;
    }

    public void setBaslik(String baslik) {
        Baslik = baslik;
    }

    public String getKullaniciAdi() {
        return KullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        KullaniciAdi = kullaniciAdi;
    }

    public String getTarih() {
        return Tarih;
    }

    public void setTarih(String tarih) {
        Tarih = tarih;
    }

    public boolean isSonuc() {
        return Sonuc;
    }

    public void setSonuc(boolean sonuc) {
        Sonuc = sonuc;
    }

    @Override
    public String toString() {
        return "YorumlarPojo{" +
                "Icerik='" + Icerik + '\'' +
                ", Baslik='" + Baslik + '\'' +
                ", KullaniciAdi='" + KullaniciAdi + '\'' +
                ", Tarih='" + Tarih + '\'' +
                ", Sonuc=" + Sonuc +
                '}';
    }
}
