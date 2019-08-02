package com.oguzzonall.e_emlakapp.Models;

public class IlanResimleriPojo {
    private String ResimYolu;

    public String getResimYolu() {
        return ResimYolu;
    }

    public void setResimYolu(String resimYolu) {
        ResimYolu = resimYolu;
    }

    @Override
    public String toString() {
        return "IlanResimleriPojo{" +
                "ResimYolu='" + ResimYolu + '\'' +
                '}';
    }
}
