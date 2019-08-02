package com.oguzzonall.e_emlakapp.Models;

public class EnCokZiyaretEdilenlerPojo {
    private int IlanId;
    private String IlanBaslik;
    private String ResimYolu;

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

    @Override
    public String toString() {
        return "EnCokZiyaretEdilenlerPojo{" +
                "IlanId=" + IlanId +
                ", IlanBaslik='" + IlanBaslik + '\'' +
                ", ResimYolu='" + ResimYolu + '\'' +
                '}';
    }
}
