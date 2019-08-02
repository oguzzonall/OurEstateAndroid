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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestApi {
    @FormUrlEncoded
    @POST("/api/login/GirisYap")
    Call<LoginPojo> login(@Field("Kad") String Kad, @Field("Sifre") String Sifre);

    @FormUrlEncoded
    @POST("/api/Kayit/KayitOl")
    Call<RegisterPojo> kayitol(@Field("Ad") String Ad, @Field("Soyad") String Soyad, @Field("Mail") String Mail, @Field("Kad") String Kad, @Field("Sifre") String Sifre, @Field("IsMusteri") boolean IsMusteri, @Field("IsSatici") boolean IsSatici);

    @FormUrlEncoded
    @POST("/api/Ilan/PostIlanVer")
    Call<IlanSonucPojo> ilanVer(@Field("BinaYasi") int BinaYasi,
                                @Field("BulunduguKat") int BulunduguKat,
                                @Field("EmlakTipi") String EmlakTipi,
                                @Field("Esyali") String Esyali,
                                @Field("Fiyat") int Fiyat,
                                @Field("KatSayisi") int KatSayisi,
                                @Field("MetreKare") int MetreKare,
                                @Field("OdaSayisi") int OdaSayisi,
                                @Field("KoordinatX") String KoordinatX,
                                @Field("KoordinatY") String KoordinatY,
                                @Field("Il") String Il,
                                @Field("Ilce") String Ilce,
                                @Field("Diger") String Diger,
                                @Field("IlanBaslik") String IlanBaslik,
                                @Field("IlanSahibi") int IlanSahibi,
                                @Field("IlanResimleri") List<String> IlanResimleri);

    @FormUrlEncoded
    @POST("/api/Yorum/YorumYap")
    Call<YorumYapPojo> yorumYap(@Field("Icerik") String Icerik, @Field("KisiId") int KisiId, @Field("YorumBasligi") String YorumBasligi, @Field("IlanId") int IlanId);

    @GET("/api/Yorum/GetYorumlarById")
    Call<List<YorumlarPojo>> GetYorumlar(@Query("id") int id);

    @GET("/api/Yorum/GetYorumlarTop3ById")
    Call<List<YorumlarPojo>> GetYorumlarTop3(@Query("id") int id);

    @GET("/api/Ilan/GetIlanDetayById")
    Call<IlanDetayPojo> GetIlanDetay(@Query("id") int id);

    @GET("/api/Ilan/GetFavoriIlanlarTop3")
    Call<List<OneCikanlarPojo>> FavoriIlanlarTop3();

    @GET("/api/Ilan/GetFavoriIlanlar")
    Call<List<OneCikanlarPojo>> FavoriIlanlar();

    @GET("/api/Rol/GetRolsById")
    Call<List<RolsPojo>> RolsById(@Query("id") int id);

    @GET("/api/Ilan/GetEnCokZiyaretEdilenlerTop3")
    Call<List<EnCokZiyaretEdilenlerPojo>> EnCokZiyaretEdilenlerTop3();

    @GET("/api/Ilan/GetEnCokZiyaretEdilenler")
    Call<List<EnCokZiyaretEdilenlerPojo>> EnCokZiyaretEdilenler();

    @GET("/api/Ilan/GetEnUygunFiyatlarTop3")
    Call<List<EnUygunFiyatlarPojo>> EnUygunFiyatlarTop3();

    @GET("/api/Ilan/GetEnUygunFiyatlar")
    Call<List<EnUygunFiyatlarPojo>> EnUygunFiyatlar();

    @GET("/api/Ilan/GetIlanResimleriListById")
    Call<List<IlanResimleriPojo>> GetIlanResimleriList(@Query("id") int id);

    @GET("/api/Ilan/GetAramaSonucuIlanlar")
    Call<List<AramaSonucuIlanlarPojo>> AramaSonucuIlanlar(@Query("Sehir") String Sehir, @Query("Ilce") String Ilce, @Query("KonutTipi") String KonutTipi, @Query("EnDusukFiyat") int EnDusukFiyat, @Query("EnYuksekFiyat") int EnYuksekFiyat);

    @GET("/api/Ilan/KisiBegeniKontrol")
    Call<BegeniKontrolPojo> BegeniKontrol(@Query("KisiID") int KisiID, @Query("IlanID") int IlanID);

    @GET("/api/Ilan/BegeniArttirAzalt")
    Call<IlanBegeniPojo> BegeniArttirAzalt(@Query("KisiID") int KisiID, @Query("IlanID") int IlanID);

    @GET("/api/Ilan/IlanGoruntulenmeKontrol")
    Call<IlanGoruntulenmeKontrolPojo> IlanGoruntulenme(@Query("KisiID") int KisiID, @Query("IlanID") int IlanID);

    @GET("/api/User/GetAdSoyadById")
    Call<UserPojo> AdSoyadById(@Query("id") int KisiID);
}
