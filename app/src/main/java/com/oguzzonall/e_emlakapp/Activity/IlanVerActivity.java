package com.oguzzonall.e_emlakapp.Activity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.oguzzonall.e_emlakapp.Adapters.MyAdapter;
import com.oguzzonall.e_emlakapp.Models.IlanSonucPojo;
import com.oguzzonall.e_emlakapp.Models.Spacecraft;
import com.oguzzonall.e_emlakapp.R;
import com.oguzzonall.e_emlakapp.RestApi.ManagerAll;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IlanVerActivity extends AppCompatActivity {
    Context context = this;
    int uyeid;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String Sehir, Ilce, EmlakTipi, EsyaliMi;
    ArrayList<Spacecraft> spacecrafts;
    ArrayList<String> sehirlerList, ilcelerList, emlakTipiList;
    ArrayAdapter<String> adapter;
    Spinner ilanVerSehirlerSpinner, ilanVerIlcelerSpinner, ilanVerEmlakTipiSpinner, ilanVerEsyaliMiSpinner;
    EditText ilanVerAdresDigerEditText, ilanVerIlanBaslikEditText, ilanVerMetreKareEditText,
            ilanVerOdaSayisiEditText, ilanVerKatSayisiEditText, ilanVerBulunduguKatEditText,
            ilanVerFiyatEditText, ilanVerBinaYasiEditText, ilanVerEnlemEditText, ilanVerBoylamEditText;
    private Button ilanVerResimEkleButton, ilanVerButton;
    private RecyclerView ilanVerResimlerRecyclerView;
    private final int CODE_MULTİPLE_IMG_GALLERY = 2;
    Bitmap bitmap;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_ver);
        tanimla();
        action();
    }

    private void tanimla() {
        ilanVerSehirlerSpinner = findViewById(R.id.ilanVerSehirlerSpinner);
        ilanVerIlcelerSpinner = findViewById(R.id.ilanVerIlcelerSpinner);
        ilanVerEmlakTipiSpinner = findViewById(R.id.ilanVerEmlakTipiSpinner);
        ilanVerEsyaliMiSpinner = findViewById(R.id.ilanVerEsyaliMiSpinner);

        ilanVerAdresDigerEditText = findViewById(R.id.ilanVerAdresDigerEditText);
        ilanVerIlanBaslikEditText = findViewById(R.id.ilanVerIlanBaslikEditText);
        ilanVerMetreKareEditText = findViewById(R.id.ilanVerMetreKareEditText);
        ilanVerOdaSayisiEditText = findViewById(R.id.ilanVerOdaSayisiEditText);
        ilanVerKatSayisiEditText = findViewById(R.id.ilanVerKatSayisiEditText);
        ilanVerBulunduguKatEditText = findViewById(R.id.ilanVerBulunduguKatEditText);
        ilanVerFiyatEditText = findViewById(R.id.ilanVerFiyatEditText);
        ilanVerEnlemEditText = findViewById(R.id.ilanVerEnlemEditText);
        ilanVerBoylamEditText = findViewById(R.id.ilanVerBoylamEditText);
        ilanVerBinaYasiEditText = findViewById(R.id.ilanVerBinaYasiEditText);

        ilanVerResimEkleButton = findViewById(R.id.ilanVerResimEkleButton);
        ilanVerButton = findViewById(R.id.ilanVerButton);

        ilanVerResimlerRecyclerView = findViewById(R.id.ilanVerResimlerRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 1);
        ilanVerResimlerRecyclerView.setLayoutManager(layoutManager);

        sehirlerList = new ArrayList<>();
        ilcelerList = new ArrayList<>();
        emlakTipiList = new ArrayList<>();

        Sehir = "İstanbul";
        Ilce = "Fatih";
        EmlakTipi = "Apart";
        EsyaliMi = "Evet";
        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        uyeid = sharedPreferences.getInt("uye_id", 0);
    }

    private void action() {
        ilanVerSehirlerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("İstanbul")) {
                    ilcelerList.clear();
                    ilcelerList.add("Fatih");
                    ilcelerList.add("Pendik");
                    ilcelerList.add("Bağcılar");
                    ilcelerList.add("Ümraniye");
                    ilcelerList.add("Üskdar");
                    adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, ilcelerList);
                    ilanVerIlcelerSpinner.setAdapter(adapter);
                } else if (parent.getItemAtPosition(position).toString().equals("Sakarya")) {
                    ilcelerList.clear();
                    ilcelerList.add("Akyazı");
                    ilcelerList.add("Adapazarı");
                    ilcelerList.add("Serdivan");
                    ilcelerList.add("Erenler");
                    ilcelerList.add("Hendek");
                    ilcelerList.add("Karasu");
                    ilcelerList.add("Arifiye");
                    ilcelerList.add("Sapanca");
                    ilcelerList.add("Geyve");
                    ilcelerList.add("Taraklı");
                    adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, ilcelerList);
                    ilanVerIlcelerSpinner.setAdapter(adapter);
                }
                Sehir = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ilanVerIlcelerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Ilce = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ilanVerEmlakTipiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EmlakTipi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ilanVerEsyaliMiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EsyaliMi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ilanVerResimEkleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Multiple IMGS
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Selection multiple images"),
                        CODE_MULTİPLE_IMG_GALLERY);
            }
        });

        ilanVerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ilanVerAdresDigerEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Adres Alanını Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ilanVerIlanBaslikEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Başlık Alanını Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ilanVerMetreKareEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Metre Kare Alanını Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ilanVerOdaSayisiEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Oda Sayısı Alanını Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ilanVerKatSayisiEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Kat Sayısı Alanını Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ilanVerBulunduguKatEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Bulunduğu Kat Alanını Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ilanVerFiyatEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Fiyat Alanını Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ilanVerBinaYasiEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Bina Yaşı Alanını Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ilanVerEnlemEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Enlem Alanını Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ilanVerBoylamEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Boylam Alanını Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (list.size() == 0 || list.isEmpty()) {
                    Toast.makeText(context, "Lütfen Resim Ekleyiniz!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    progressDialog=new ProgressDialog(context);
                    progressDialog.setMessage("Ilan Verme İşleminiz Gerçekleştiriliyor...");
                    progressDialog.show();
                    String Adres = ilanVerAdresDigerEditText.getText().toString().trim();
                    String Baslik = ilanVerIlanBaslikEditText.getText().toString().trim();
                    int MetreKare = Integer.valueOf(ilanVerMetreKareEditText.getText().toString().trim());
                    int OdaSayisi = Integer.valueOf(ilanVerOdaSayisiEditText.getText().toString().trim());
                    int KatSayisi = Integer.valueOf(ilanVerKatSayisiEditText.getText().toString().trim());
                    int BulunduguKat = Integer.valueOf(ilanVerBulunduguKatEditText.getText().toString().trim());
                    int Fiyat = Integer.valueOf(ilanVerFiyatEditText.getText().toString().trim());
                    int BinaYasi = Integer.valueOf(ilanVerBinaYasiEditText.getText().toString().trim());
                    String Enlem = ilanVerEnlemEditText.getText().toString().trim();
                    String Boylam = ilanVerBoylamEditText.getText().toString().trim();

                    ilanVerPost(BinaYasi, BulunduguKat, EmlakTipi, EsyaliMi, Fiyat, KatSayisi, MetreKare, OdaSayisi, Enlem, Boylam, Sehir, Ilce, Adres, Baslik, uyeid, list);
                }
            }
        });

    }

    public void ilanVerPost(int BinaYasi, int BulunduguKat, String EmlakTipi, String EsyaliMi,
                            int Fiyat, int KatSayisi, int MetreKare,
                            int OdaSayisi, String Enlem,
                            String Boylam, String Sehir,
                            String Ilce, String Adres,
                            String Baslik, int IlanSahibi,
                            List<String> IlanResimleri) {
        Call<IlanSonucPojo> request = ManagerAll.getGetInstanse().PostIlanVer(BinaYasi, BulunduguKat, EmlakTipi, EsyaliMi, Fiyat, KatSayisi, MetreKare, OdaSayisi, Enlem, Boylam, Sehir, Ilce, Adres, Baslik, IlanSahibi, IlanResimleri);
        request.enqueue(new Callback<IlanSonucPojo>() {
            @Override
            public void onResponse(Call<IlanSonucPojo> call, Response<IlanSonucPojo> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSonuc()) {
                        Toast.makeText(context, "İlan Verme İşleminiz Tamamlanmıştır.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(IlanVerActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(context, "İlan Verme Sırasında Bir Hata Oluştu.Lütfen Tekrar Deneyiniz.", Toast.LENGTH_LONG).show();
                    }
                }
                progressDialog.cancel();
            }

            @Override
            public void onFailure(Call<IlanSonucPojo> call, Throwable t) {
                Toast.makeText(context, "İnternet Bağlantınızı Kontrol Ediniz.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_MULTİPLE_IMG_GALLERY && resultCode == RESULT_OK) {
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                Spacecraft s;
                spacecrafts = new ArrayList<>();
                list = new ArrayList<>();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] byt = byteArrayOutputStream.toByteArray();
                    String imagetoString = Base64.encodeToString(byt, Base64.DEFAULT);
                    list.add(imagetoString);
                    s = new Spacecraft();
                    s.setUri(uri);
                    spacecrafts.add(s);
                }
                ilanVerResimlerRecyclerView.setAdapter(new MyAdapter(this, spacecrafts));
            }
        }

    }
}
