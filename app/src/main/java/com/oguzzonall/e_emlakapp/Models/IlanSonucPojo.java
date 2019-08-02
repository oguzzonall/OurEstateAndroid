package com.oguzzonall.e_emlakapp.Models;

public class IlanSonucPojo {
    private int IlanId;
    private boolean Sonuc;

    public int getIlanId() {
        return IlanId;
    }

    public void setIlanId(int ilanId) {
        IlanId = ilanId;
    }

    public boolean isSonuc() {
        return Sonuc;
    }

    public void setSonuc(boolean sonuc) {
        Sonuc = sonuc;
    }

    @Override
    public String toString() {
        return "IlanSonucPojo{" +
                "IlanId=" + IlanId +
                ", Sonuc=" + Sonuc +
                '}';
    }
}
