package com.oguzzonall.e_emlakapp.Models;

public class AramaSonucuIlanlarPojo {
    private int IlanId;
    private String IlanBaslik;
    private String ResimYolu;
    private String Aciklama;
    private boolean Sonuc;

    public int getIlanId() {
        return IlanId;
    }

    public void setIlanId(int ilanId) {
        IlanId = ilanId;
    }

    public String getIlanBaslik() {
        return IlanBaslik;
    }

    public void setIlanBaslik(String ilanBaslik) {
        IlanBaslik = ilanBaslik;
    }

    public String getResimYolu() {
        return ResimYolu;
    }

    public void setResimYolu(String resimYolu) {
        ResimYolu = resimYolu;
    }

    public String getAciklama() {
        return Aciklama;
    }

    public void setAciklama(String aciklama) {
        Aciklama = aciklama;
    }

    public boolean isSonuc() {
        return Sonuc;
    }

    public void setSonuc(boolean sonuc) {
        Sonuc = sonuc;
    }

    @Override
    public String toString() {
        return "AramaSonucuIlanlarPojo{" +
                "IlanId=" + IlanId +
                ", IlanBaslik='" + IlanBaslik + '\'' +
                ", ResimYolu='" + ResimYolu + '\'' +
                ", Aciklama='" + Aciklama + '\'' +
                ", Sonuc=" + Sonuc +
                '}';
    }
}
