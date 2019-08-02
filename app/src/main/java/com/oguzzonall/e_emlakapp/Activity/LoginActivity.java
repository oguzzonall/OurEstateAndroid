package com.oguzzonall.e_emlakapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzzonall.e_emlakapp.Models.LoginPojo;
import com.oguzzonall.e_emlakapp.R;
import com.oguzzonall.e_emlakapp.RestApi.ManagerAll;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Context context = this;
    TextView kayitOlGitTextView;
    EditText girisYapKullaniciAdiEditText, girisYapPasswordEditText;
    TextInputLayout girisYapKullaniciAdiInputLayout, girisYapPasswordTextInputLayout;
    Button girisYapButton;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tanimla();
        action();
    }

    private void tanimla() {
        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        if (sharedPreferences.getInt("uye_id", 0) != 0 && sharedPreferences.getString("uye_KullaniciAdi", null) != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        girisYapKullaniciAdiInputLayout = findViewById(R.id.girisYapKullaniciAdiInputLayout);
        girisYapPasswordTextInputLayout = findViewById(R.id.girisYapPasswordTextInputLayout);
        kayitOlGitTextView = findViewById(R.id.kayitOlGitTextView);
        girisYapPasswordEditText = findViewById(R.id.girisYapPasswordEditText);
        girisYapKullaniciAdiEditText = findViewById(R.id.girisYapKullaniciAdiEditText);
        girisYapButton = findViewById(R.id.girisYapButton);
    }

    private void action() {
        kayitOlGitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, KayitOlActivity.class);
                startActivity(intent);
                finish();
            }
        });

        girisYapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kad = girisYapKullaniciAdiEditText.getText().toString().trim();
                String sifre = girisYapPasswordEditText.getText().toString().trim();
                if (kad.isEmpty() || sifre.isEmpty()) {
                    if (kad.trim().isEmpty())
                        girisYapKullaniciAdiInputLayout.setError("Kullanıcı Adı Boş Bırakılamaz");
                    else if (sifre.trim().isEmpty())
                        girisYapPasswordTextInputLayout.setError("Şifre Boş Bırakılamaz");
                    return;
                }
                else if (sifre.length() < 6) {
                    girisYapPasswordTextInputLayout.setError("Şifrenin Uzunluğu En Az 6 Karakter Olmalıdır.");
                    return;
                }
                else
                    login(kad, sifre);
            }
        });
    }

    public void login(String kad, String sifre) {
        Call<LoginPojo> loginrequest = ManagerAll.getGetInstanse().login(kad, sifre);
        loginrequest.enqueue(new Callback<LoginPojo>() {
            @Override
            public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSonuc()) {
                        int uyeId = response.body().getKisiId();
                        String kullaniciAdi = response.body().getKad();
                        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("uye_id", uyeId);
                        editor.putString("uye_KullaniciAdi", kullaniciAdi);
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, response.body().getMesaj(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginPojo> call, Throwable t) {

            }
        });
    }
}
