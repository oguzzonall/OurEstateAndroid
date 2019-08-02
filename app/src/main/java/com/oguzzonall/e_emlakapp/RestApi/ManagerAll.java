package com.oguzzonall.e_emlakapp.RestApi;

import com.oguzzonall.e_emlakapp.Models.AramaSonucuIlanlarPojo;
import com.oguzzonall.e_emlakapp.Models.BegeniKontrolPojo;
import com.oguzzonall.e_emlakapp.Models.EnCokZiyaretEdilenlerPojo;
import com.oguzzonall.e_emlakapp.Models.EnUygunFiyatlarPojo;
import com.oguzzonall.e_emlakapp.Models.IlanBegeniPojo;
import com.oguzzonall.e_emlakapp.Models.IlanDetayPojo;
import com.oguzzonall.e_emlakapp.Models.IlanGoruntulenmeKontrolPojo;
import com.oguzzonall.e_emlakapp.Models.IlanResimleriPojo;
import com.oguzzonall.e_emlakapp.Models.IlanSonucPojo;
import com.oguzzonall.e_emlakapp.Models.LoginPojo;
import com.oguzzonall.e_emlakapp.Models.OneCikanlarPojo;
import com.oguzzonall.e_emlakapp.Models.RegisterPojo;
import com.oguzzonall.e_emlakapp.Models.RolsPojo;
import com.oguzzonall.e_emlakapp.Models.UserPojo;
import com.oguzzonall.e_emlakapp.Models.YorumYapPojo;
import com.oguzzonall.e_emlakapp.Models.YorumlarPojo;

import java.util.List;

import retrofit2.Call;

public class ManagerAll extends BaseManager {
    private static ManagerAll ourGetInstanse = new ManagerAll();

    public static synchronized ManagerAll getGetInstanse() {
        return ourGetInstanse;
    }

    public Call<LoginPojo> login(String username, String password) {
        Call<LoginPojo> x = getRestApiClient().login(username, password);
        return x;
    }

    public Call<YorumYapPojo> yorumYapIlan(String Icerik, int KisiId, String YorumBasligi, int IlanId) {
        Call<YorumYapPojo> x = getRestApiClient().yorumYap(Icerik, KisiId, YorumBasligi, IlanId);
        return x;
    }

    public Call<List<YorumlarPojo>> GetYorumlarTop3ById(int id) {
        Call<List<YorumlarPojo>> x = getRestApiClient().GetYorumlarTop3(id);
        return x;
    }

    public Call<List<YorumlarPojo>> GetYorumlarById(int id) {
        Call<List<YorumlarPojo>> x = getRestApiClient().GetYorumlar(id);
        return x;
    }

    public Call<RegisterPojo> register(String Ad, String Soyad, String Mail, String Kad, String Sifre, boolean IsMusteri, boolean IsSatici) {
        Call<RegisterPojo> x = getRestApiClient().kayitol(Ad, Soyad, Mail, Kad, Sifre, IsMusteri, IsSatici);
        return x;
    }


    public Call<IlanSonucPojo> PostIlanVer(int BinaYasi, int BulunduguKat, String EmlakTipi, String Esyali,
                                           int Fiyat, int KatSayisi, int MetreKare,
                                           int OdaSayisi, String KoordinatX,
                                           String KoordinatY, String Il,
                                           String Ilce, String Diger,
                                           String IlanBaslik, int IlanSahibi,
                                           List<String> IlanResimleri) {
        Call<IlanSonucPojo> x = getRestApiClient().ilanVer(BinaYasi, BulunduguKat, EmlakTipi, Esyali, Fiyat, KatSayisi, MetreKare, OdaSayisi, KoordinatX, KoordinatY, Il, Ilce, Diger, IlanBaslik, IlanSahibi, IlanResimleri);
        return x;
    }

    public Call<IlanDetayPojo> GetIlanDetayById(int id) {
        Call<IlanDetayPojo> x = getRestApiClient().GetIlanDetay(id);
        return x;
    }

    public Call<List<OneCikanlarPojo>> GetFavoriIlanlarTop3() {
        Call<List<OneCikanlarPojo>> x = getRestApiClient().FavoriIlanlarTop3();
        return x;
    }

    public Call<List<OneCikanlarPojo>> GetFavoriIlanlar() {
        Call<List<OneCikanlarPojo>> x = getRestApiClient().FavoriIlanlar();
        return x;
    }

    public Call<List<EnCokZiyaretEdilenlerPojo>> GetEnCokZiyaretEdilenlerTop3() {
        Call<List<EnCokZiyaretEdilenlerPojo>> x = getRestApiClient().EnCokZiyaretEdilenlerTop3();
        return x;
    }

    public Call<List<EnCokZiyaretEdilenlerPojo>> GetEnCokZiyaretEdilenler() {
        Call<List<EnCokZiyaretEdilenlerPojo>> x = getRestApiClient().EnCokZiyaretEdilenler();
        return x;
    }

    public Call<List<EnUygunFiyatlarPojo>> GetEnUygunFiyatlarTop3() {
        Call<List<EnUygunFiyatlarPojo>> x = getRestApiClient().EnUygunFiyatlarTop3();
        return x;
    }

    public Call<List<EnUygunFiyatlarPojo>> GetEnUygunFiyatlar() {
        Call<List<EnUygunFiyatlarPojo>> x = getRestApiClient().EnUygunFiyatlar();
        return x;
    }

    public Call<List<IlanResimleriPojo>> GetIlanResimleriListById(int id) {
        Call<List<IlanResimleriPojo>> x = getRestApiClient().GetIlanResimleriList(id);
        return x;
    }

    public Call<List<RolsPojo>> GetRolsById(int id) {
        Call<List<RolsPojo>> x = getRestApiClient().RolsById(id);
        return x;
    }

    public Call<List<AramaSonucuIlanlarPojo>> GetAramaSonucuIlanlar(String Sehir, String Ilce, String KonutTipi, int EnDusukFiyat, int EnYuksekFiyat) {
        Call<List<AramaSonucuIlanlarPojo>> x = getRestApiClient().AramaSonucuIlanlar(Sehir, Ilce, KonutTipi, EnDusukFiyat, EnYuksekFiyat);
        return x;
    }


    public Call<BegeniKontrolPojo> KisiBegeniKontrol(int KisiID, int IlanID) {
        Call<BegeniKontrolPojo> x = getRestApiClient().BegeniKontrol(KisiID, IlanID);
        return x;
    }

    public Call<IlanBegeniPojo> IlanBegeniArttirAzalt(int KisiID, int IlanID) {
        Call<IlanBegeniPojo> x = getRestApiClient().BegeniArttirAzalt(KisiID, IlanID);
        return x;
    }

    public Call<IlanGoruntulenmeKontrolPojo> IlanGoruntulenmeKontrol(int KisiID, int IlanID) {
        Call<IlanGoruntulenmeKontrolPojo> x = getRestApiClient().IlanGoruntulenme(KisiID, IlanID);
        return x;
    }

    public Call<UserPojo> GetAdSoyadById(int KisiID) {
        Call<UserPojo> x = getRestApiClient().AdSoyadById(KisiID);
        return x;
    }
}
