package com.oguzzonall.e_emlakapp.Models;

public class BegeniKontrolPojo {
    private boolean IsBegeni;

    public boolean isBegeni() {
        return IsBegeni;
    }

    public void setBegeni(boolean begeni) {
        IsBegeni = begeni;
    }

    @Override
    public String toString() {
        return "BegeniKontrolPojo{" +
                "IsBegeni=" + IsBegeni +
                '}';
    }
}
