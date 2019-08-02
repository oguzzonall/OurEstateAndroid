package com.oguzzonall.e_emlakapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oguzzonall.e_emlakapp.Adapters.EvDetaySliderAdapter;
import com.oguzzonall.e_emlakapp.Adapters.YorumlarAdapter;
import com.oguzzonall.e_emlakapp.Models.BegeniKontrolPojo;
import com.oguzzonall.e_emlakapp.Models.IlanBegeniPojo;
import com.oguzzonall.e_emlakapp.Models.IlanDetayPojo;
import com.oguzzonall.e_emlakapp.Models.IlanGoruntulenmeKontrolPojo;
import com.oguzzonall.e_emlakapp.Models.IlanResimleriPojo;
import com.oguzzonall.e_emlakapp.Models.RolsPojo;
import com.oguzzonall.e_emlakapp.Models.YorumYapPojo;
import com.oguzzonall.e_emlakapp.Models.YorumlarPojo;
import com.oguzzonall.e_emlakapp.R;
import com.oguzzonall.e_emlakapp.RestApi.ManagerAll;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EvDetayActivity extends AppCompatActivity implements OnMapReadyCallback {
    Context context = this;
    int ilanId, uyeid;
    Double enlem = 34.5, boylam = 21.3;
    String title, otherId;
    SharedPreferences sharedPreferences;
    ViewPager evDetayActivityAnaSlider;
    EvDetaySliderAdapter evDetayActivitySliderAdapter;
    CircleIndicator evDetayActivitySliderCircle;
    TextView emlakTipiTextView, adresTextView, metreKareTextView, odaSayisiTextView, katSayisiTextView, bulunduguKatTextView, fiyatTextView,
            binaYasiTextView, esyaliTextview, saticiBilgileriTextView, begenmeSayisiTextView, goruntulenmeSayisiTextView, evDetayActivityTumYorumlarTextView;
    ImageView begenmeImageView;
    MapView mapView;
    GoogleMap gm;
    RecyclerView yorumlarRecyclerView;
    YorumlarAdapter yorumlarAdapter;
    EditText yorumBaslikEditText, yorumIcerikEditText;
    Button yorumGonderButton, evDetayActivityMesajGonderButton;
    List<IlanResimleriPojo> list;
    List<RolsPojo> rolsPojoList;
    Timer timer;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ev_detay);
        Bundle bundle = getIntent().getExtras();
        ilanId = bundle.getInt("ilanId");
        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        uyeid = sharedPreferences.getInt("uye_id", 0);
        tanimla();
        action();
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        progressDialog.setMessage("Evin Detayları Yükleniyor...");
        progressDialog.show();
        getEvDetayById(ilanId);
        getIlanResimleriById(ilanId);
        IlanGoruntulenmeKontrol();
        KisiBegeniKontrol();
        GetYorumlarTop3ById(ilanId);
        progressDialog.cancel();
    }

    public void tanimla() {
        evDetayActivityAnaSlider = findViewById(R.id.evDetayActivityAnaSlider);

        evDetayActivitySliderCircle = findViewById(R.id.evDetayActivityAnaSliderCircle);

        emlakTipiTextView = findViewById(R.id.emlakTipiTextView);
        adresTextView = findViewById(R.id.adresTextView);
        metreKareTextView = findViewById(R.id.metreKareTextView);
        odaSayisiTextView = findViewById(R.id.odaSayisiTextView);
        katSayisiTextView = findViewById(R.id.katSayisiTextView);
        bulunduguKatTextView = findViewById(R.id.bulunduguKatTextView);
        fiyatTextView = findViewById(R.id.fiyatTextView);
        binaYasiTextView = findViewById(R.id.binaYasiTextView);
        esyaliTextview = findViewById(R.id.esyaliTextview);
        saticiBilgileriTextView = findViewById(R.id.saticiBilgileriTextView);
        begenmeSayisiTextView = findViewById(R.id.begenmeSayisiTextView);
        goruntulenmeSayisiTextView = findViewById(R.id.goruntulenmeSayisiTextView);
        evDetayActivityTumYorumlarTextView = findViewById(R.id.evDetayActivityTumYorumlarTextView);

        begenmeImageView = findViewById(R.id.begenmeImageView);

        mapView = findViewById(R.id.evDetayMapView);

        yorumlarRecyclerView = findViewById(R.id.yorumlarRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(EvDetayActivity.this, 1);
        yorumlarRecyclerView.setLayoutManager(layoutManager);

        yorumBaslikEditText = findViewById(R.id.yorumBaslikEditText);
        yorumIcerikEditText = findViewById(R.id.yorumIcerikEditText);

        yorumGonderButton = findViewById(R.id.yorumGonderButton);
        evDetayActivityMesajGonderButton = findViewById(R.id.evDetayActivityMesajGonderButton);

        progressDialog = new ProgressDialog(context);
    }

    public void action() {
        begenmeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IlanBegeniArttirAzalt(uyeid, ilanId);
            }
        });

        yorumGonderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yorumBaslik = yorumBaslikEditText.getText().toString().trim();
                String yorumIcerik = yorumIcerikEditText.getText().toString().trim();

                if (yorumBaslik.isEmpty()) {
                    Toast.makeText(context, "Başlık Kısmını Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (yorumIcerik.isEmpty()) {
                    Toast.makeText(context, "İçerik Kısmını Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    yorumYapIlan(ilanId, uyeid, yorumBaslik, yorumIcerik);
                }
            }
        });

        evDetayActivityMesajGonderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EvDetayActivity.this, ChatActivity.class);
                intent.putExtra("otherId", otherId);
                intent.putExtra("username", saticiBilgileriTextView.getText().toString().trim());
                startActivity(intent);
            }
        });

        evDetayActivityTumYorumlarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EvDetayActivity.this, TumYorumlarActivity.class);
                intent.putExtra("ilanId", ilanId);
                startActivity(intent);
            }
        });
    }

    public void yorumYapIlan(final int ilanId, int uyeid, String yorumBaslik, String yorumIcerik) {
        Call<YorumYapPojo> request = ManagerAll.getGetInstanse().yorumYapIlan(yorumIcerik, uyeid, yorumBaslik, ilanId);
        request.enqueue(new Callback<YorumYapPojo>() {
            @Override
            public void onResponse(Call<YorumYapPojo> call, Response<YorumYapPojo> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSonuc()) {
                        Toast.makeText(context, response.body().getMesaj(), Toast.LENGTH_SHORT).show();
                        GetYorumlarTop3ById(ilanId);
                    } else {
                        Toast.makeText(context, response.body().getMesaj(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<YorumYapPojo> call, Throwable t) {

            }
        });
    }

    public void getEvDetayById(final int ilanId) {
        Call<IlanDetayPojo> request = ManagerAll.getGetInstanse().GetIlanDetayById(ilanId);
        request.enqueue(new Callback<IlanDetayPojo>() {
            @Override
            public void onResponse(Call<IlanDetayPojo> call, Response<IlanDetayPojo> response) {
                if (response.isSuccessful()) {
                    IlanDetayPojo ilandetay = response.body();
                    emlakTipiTextView.setText(ilandetay.getEmlakTipi());
                    adresTextView.setText(ilandetay.getIl() + " " + ilandetay.getIlce() + " " + ilandetay.getDiger());
                    metreKareTextView.setText("" + ilandetay.getMetreKare());
                    odaSayisiTextView.setText("" + ilandetay.getOdaSayisi());
                    katSayisiTextView.setText("" + ilandetay.getKatSayisi());
                    bulunduguKatTextView.setText("" + ilandetay.getBulunduguKat());
                    begenmeSayisiTextView.setText("" + ilandetay.getBegenmeSayisi());
                    binaYasiTextView.setText("" + ilandetay.getBinaYasi());
                    fiyatTextView.setText("" + ilandetay.getFiyat());
                    esyaliTextview.setText(ilandetay.getEsyali());
                    goruntulenmeSayisiTextView.setText("" + ilandetay.getGoruntulenmeSayisi());
                    saticiBilgileriTextView.setText(ilandetay.getEmlakSahibi());
                    enlem = Double.parseDouble(ilandetay.getKoordinatX());
                    boylam = Double.parseDouble(ilandetay.getKoordinatY());
                    title = ilandetay.getIlanBaslik();
                    otherId = ilandetay.getOtherId();
                    mapView.getMapAsync(EvDetayActivity.this);
                    evDetayActivityTumYorumlarTextView.setText(evDetayActivityTumYorumlarTextView.getText() + "(" + response.body().getYorumSayisi() + ")");
                    if (uyeid != Integer.valueOf(otherId) && uyeid != 0)
                        evDetayActivityMesajGonderButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<IlanDetayPojo> call, Throwable t) {

            }
        });
    }

    public void getIlanResimleriById(int ilanId) {
        list = new ArrayList<>();
        Call<List<IlanResimleriPojo>> request = ManagerAll.getGetInstanse().GetIlanResimleriListById(ilanId);
        request.enqueue(new Callback<List<IlanResimleriPojo>>() {
            @Override
            public void onResponse(Call<List<IlanResimleriPojo>> call, Response<List<IlanResimleriPojo>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    evDetayActivitySliderAdapter = new EvDetaySliderAdapter(list, context);
                    evDetayActivityAnaSlider.setAdapter(evDetayActivitySliderAdapter);
                    evDetayActivitySliderCircle.setViewPager(evDetayActivityAnaSlider);
                    evDetayActivitySliderCircle.bringToFront();
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
                }
            }

            @Override
            public void onFailure(Call<List<IlanResimleriPojo>> call, Throwable t) {

            }
        });
    }

    public void IlanGoruntulenmeKontrol() {
        Call<IlanGoruntulenmeKontrolPojo> request = ManagerAll.getGetInstanse().IlanGoruntulenmeKontrol(uyeid, ilanId);
        request.enqueue(new Callback<IlanGoruntulenmeKontrolPojo>() {
            @Override
            public void onResponse(Call<IlanGoruntulenmeKontrolPojo> call, Response<IlanGoruntulenmeKontrolPojo> response) {

            }

            @Override
            public void onFailure(Call<IlanGoruntulenmeKontrolPojo> call, Throwable t) {

            }
        });
    }

    public void KisiBegeniKontrol() {
        Call<BegeniKontrolPojo> request = ManagerAll.getGetInstanse().KisiBegeniKontrol(uyeid, ilanId);
        request.enqueue(new Callback<BegeniKontrolPojo>() {
            @Override
            public void onResponse(Call<BegeniKontrolPojo> call, Response<BegeniKontrolPojo> response) {
                if (response.isSuccessful()) {
                    if (response.body().isBegeni()) {
                        begenmeImageView.setImageResource(R.mipmap.likeaktif);
                    }
                }
            }

            @Override
            public void onFailure(Call<BegeniKontrolPojo> call, Throwable t) {

            }
        });
    }

    public void IlanBegeniArttirAzalt(final int KisiID, final int IlanID) {
        Call<IlanBegeniPojo> request = ManagerAll.getGetInstanse().IlanBegeniArttirAzalt(KisiID, IlanID);
        request.enqueue(new Callback<IlanBegeniPojo>() {
            @Override
            public void onResponse(Call<IlanBegeniPojo> call, Response<IlanBegeniPojo> response) {
                if (response.isSuccessful()) {
                    Log.i("feafef1", "İstek Atıo" + response.body() + " " + KisiID + "" + IlanID);
                    if (response.body().isSonuc()) {
                        begenmeSayisiTextView.setText("" + response.body().getBegenmeSayisi());
                        if (response.body().isBegeni()) {
                            begenmeImageView.setImageResource(R.mipmap.likeaktif);
                        } else {
                            begenmeImageView.setImageResource(R.mipmap.likepasif);
                        }
                    } else {
                        Toast.makeText(context, response.body().getMesaj(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<IlanBegeniPojo> call, Throwable t) {

            }
        });
    }

    public void GetYorumlarTop3ById(int ilanId) {
        Call<List<YorumlarPojo>> request = ManagerAll.getGetInstanse().GetYorumlarTop3ById(ilanId);
        request.enqueue(new Callback<List<YorumlarPojo>>() {
            @Override
            public void onResponse(Call<List<YorumlarPojo>> call, Response<List<YorumlarPojo>> response) {
                if (response.isSuccessful()) {
                    if (response.body().get(0).Sonuc) {
                        yorumlarAdapter = new YorumlarAdapter(context, response.body());
                        yorumlarRecyclerView.setAdapter(yorumlarAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<YorumlarPojo>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gm = googleMap;
        LatLng latLng = new LatLng(enlem, boylam);
        gm.addMarker(new MarkerOptions().position(latLng).title(title));
        gm.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        gm.moveCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onPause() {
        timer.cancel();
        super.onPause();
    }

    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            EvDetayActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (evDetayActivityAnaSlider.getCurrentItem() != list.size()-1) {
                        evDetayActivityAnaSlider.setCurrentItem(evDetayActivityAnaSlider.getCurrentItem() + 1);
                    } else {
                        evDetayActivityAnaSlider.setCurrentItem(0);
                    }
                    /*
                    * if (mainActivityAnaSlider.getCurrentItem() == 0) {
                        mainActivityAnaSlider.setCurrentItem(1);
                    } else if (mainActivityAnaSlider.getCurrentItem() == 1) {
                        mainActivityAnaSlider.setCurrentItem(2);
                    } else {
                        mainActivityAnaSlider.setCurrentItem(0);
                    }*/
                }
            });
        }
    }
}
