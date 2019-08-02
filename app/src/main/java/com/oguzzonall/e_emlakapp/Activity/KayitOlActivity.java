package com.oguzzonall.e_emlakapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzzonall.e_emlakapp.Models.RegisterPojo;
import com.oguzzonall.e_emlakapp.R;
import com.oguzzonall.e_emlakapp.RestApi.ManagerAll;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KayitOlActivity extends AppCompatActivity {
    Context context = this;
    TextView girisYapGitTextView;
    TextInputLayout kayitOlAdiInputLayout, kayitOlSoyadInputLayout, kayitOlGmailInputLayout, kayitOlKullaniciAdiInputLayout, kayitOlPasswordTextInputLayout, kayitOlPasswordAgainTextInputLayout;
    EditText kayitOlAdiEditText, kayitOlSoyadEditText, kayitOLMailEditText, kayitOlKullaniciEditText, kayitOlPasswordEditText, kayitOlPasswordAgainEditText;
    CheckBox kayitOlMusteriyimCheckBox, kayitOlSaticiyimCheckBox;
    Button kayitOlButton;
    boolean IsMusteri = false, IsSatici = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        tanimla();
        action();
    }

    private void tanimla() {
        girisYapGitTextView = findViewById(R.id.girisYapGitTextView);

        kayitOlAdiInputLayout = findViewById(R.id.kayitOlAdiInputLayout);
        kayitOlSoyadInputLayout = findViewById(R.id.kayitOlSoyadInputLayout);
        kayitOlGmailInputLayout = findViewById(R.id.kayitOlGmailInputLayout);
        kayitOlKullaniciAdiInputLayout = findViewById(R.id.kayitOlKullaniciAdiInputLayout);
        kayitOlPasswordTextInputLayout = findViewById(R.id.kayitOlPasswordTextInputLayout);
        kayitOlPasswordAgainTextInputLayout = findViewById(R.id.kayitOlPasswordAgainTextInputLayout);

        kayitOlAdiEditText = findViewById(R.id.kayitOlAdiEditText);
        kayitOlSoyadEditText = findViewById(R.id.kayitOlSoyadEditText);
        kayitOLMailEditText = findViewById(R.id.kayitOLMailEditText);
        kayitOlKullaniciEditText = findViewById(R.id.kayitOlKullaniciEditText);
        kayitOlPasswordEditText = findViewById(R.id.kayitOlPasswordEditText);
        kayitOlPasswordAgainEditText = findViewById(R.id.kayitOlPasswordAgainEditText);

        kayitOlMusteriyimCheckBox = findViewById(R.id.kayitOlMusteriyimCheckBox);
        kayitOlSaticiyimCheckBox = findViewById(R.id.kayitOlSaticiyimCheckBox);

        kayitOlButton = findViewById(R.id.kayitOlButton);
    }

    private void action() {
        girisYapGitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        kayitOlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ad = kayitOlAdiEditText.getText().toString().trim();
                String soyad = kayitOlSoyadEditText.getText().toString().trim();
                String mail = kayitOLMailEditText.getText().toString().trim();
                String kad = kayitOlKullaniciEditText.getText().toString().trim();
                String pass = kayitOlPasswordEditText.getText().toString().trim();
                String againpass = kayitOlPasswordAgainEditText.getText().toString().trim();
                IsMusteri = kayitOlMusteriyimCheckBox.isChecked();
                IsSatici = kayitOlMusteriyimCheckBox.isChecked();

                if (ad.isEmpty() || soyad.isEmpty() || mail.isEmpty() || kad.isEmpty() || pass.isEmpty() || againpass.isEmpty()) {
                    if (ad.isEmpty())
                        kayitOlAdiInputLayout.setError("Ad Alanı Boş Bırakılamaz");
                    else if (soyad.isEmpty())
                        kayitOlSoyadInputLayout.setError("Soyad Alanı Boş Bırakılamaz");
                    else if (mail.isEmpty())
                        kayitOlGmailInputLayout.setError("Mail Alanı Boş Bırakılamaz");
                    else if (kad.isEmpty())
                        kayitOlKullaniciAdiInputLayout.setError("Kullanıcı Adı Alanı Boş Bırakılamaz");
                    else if (pass.isEmpty())
                        kayitOlPasswordTextInputLayout.setError("Şifre Alanı Alanı Boş Bırakılamaz");
                    else if (againpass.isEmpty())
                        kayitOlPasswordAgainTextInputLayout.setError("Şifre Tekrar Alanı Boş Bırakılamaz");
                    return;
                } else if (pass.length() < 6) {
                    kayitOlPasswordTextInputLayout.setError("Şifrenin Uzunluğu En Az 6 Karakter Olmalıdır.");
                    return;
                } else if (againpass.length() < 6) {
                    kayitOlPasswordTextInputLayout.setError("Şifrenin Uzunluğu En Az 6 Karakter Olmalıdır.");
                    return;
                } else if (pass.equals(againpass) == false) {
                    Toast.makeText(context, "Şifre ve Şifre Tekrar Eşleşmiyor", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    kayitol(ad, soyad, mail, kad, pass, IsMusteri, IsSatici);
            }

            public void kayitol(String ad, String soyad, String mail, String kad, String pass, Boolean IsMusteri, Boolean IsSatici) {
                Call<RegisterPojo> request = ManagerAll.getGetInstanse().register(ad, soyad, mail, kad, pass, IsMusteri, IsSatici);
                request.enqueue(new Callback<RegisterPojo>() {
                    @Override
                    public void onResponse(Call<RegisterPojo> call, Response<RegisterPojo> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isSonuc()) {
                                Toast.makeText(context, "Kayit Olma İşlemi Başarılı", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(KayitOlActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(context, response.body().getMesaj(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterPojo> call, Throwable t) {
                        Toast.makeText(context, "Lütfen İnternet Bağlantınızı Kontrol Ediniz.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
