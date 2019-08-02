package com.oguzzonall.e_emlakapp.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.oguzzonall.e_emlakapp.Adapters.EnCokZiyaretEdilenlerAdapter;
import com.oguzzonall.e_emlakapp.Models.EnCokZiyaretEdilenlerPojo;
import com.oguzzonall.e_emlakapp.R;
import com.oguzzonall.e_emlakapp.RestApi.ManagerAll;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnCokZiyaretEdilenlerActivity extends AppCompatActivity {
    RecyclerView enCokZiyaretEdilenlerActivityRecyclerView;
    Context context = this;
    List<EnCokZiyaretEdilenlerPojo> list;
    EnCokZiyaretEdilenlerAdapter enCokZiyaretEdilenlerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_en_cok_ziyaret_edilenler);
        tanimla();
        getEnCokZiyaretEdilenler();
    }

    public void tanimla() {
        enCokZiyaretEdilenlerActivityRecyclerView = findViewById(R.id.enCokZiyaretEdilenlerActivityRecyclerView);
        list = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(EnCokZiyaretEdilenlerActivity.this, 3);
        enCokZiyaretEdilenlerActivityRecyclerView.setLayoutManager(layoutManager);
    }

    public void getEnCokZiyaretEdilenler() {
        Call<List<EnCokZiyaretEdilenlerPojo>> request = ManagerAll.getGetInstanse().GetEnCokZiyaretEdilenler();
        request.enqueue(new Callback<List<EnCokZiyaretEdilenlerPojo>>() {
            @Override
            public void onResponse(Call<List<EnCokZiyaretEdilenlerPojo>> call, Response<List<EnCokZiyaretEdilenlerPojo>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    enCokZiyaretEdilenlerAdapter = new EnCokZiyaretEdilenlerAdapter(context, list, EnCokZiyaretEdilenlerActivity.this);
                    enCokZiyaretEdilenlerActivityRecyclerView.setAdapter(enCokZiyaretEdilenlerAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<EnCokZiyaretEdilenlerPojo>> call, Throwable t) {

            }
        });
    }
}
