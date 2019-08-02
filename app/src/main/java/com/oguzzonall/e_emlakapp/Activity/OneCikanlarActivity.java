package com.oguzzonall.e_emlakapp.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.oguzzonall.e_emlakapp.Adapters.EnCokZiyaretEdilenlerAdapter;
import com.oguzzonall.e_emlakapp.Adapters.OneCikanlarAdapter;
import com.oguzzonall.e_emlakapp.Models.EnCokZiyaretEdilenlerPojo;
import com.oguzzonall.e_emlakapp.Models.OneCikanlarPojo;
import com.oguzzonall.e_emlakapp.R;
import com.oguzzonall.e_emlakapp.RestApi.ManagerAll;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OneCikanlarActivity extends AppCompatActivity {
    Context context = this;
    List<OneCikanlarPojo> list;
    RecyclerView oneCikanlarActivityRecyclerView;
    OneCikanlarAdapter oneCikanlarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_cikanlar);
        tanimla();
        getOneCikanlar();
    }

    public void tanimla() {
        list = new ArrayList<>();
        oneCikanlarActivityRecyclerView = findViewById(R.id.oneCikanlarActivityRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(OneCikanlarActivity.this, 3);
        oneCikanlarActivityRecyclerView.setLayoutManager(layoutManager);
    }


    public void getOneCikanlar() {
        Call<List<OneCikanlarPojo>> request = ManagerAll.getGetInstanse().GetFavoriIlanlar();
        request.enqueue(new Callback<List<OneCikanlarPojo>>() {
            @Override
            public void onResponse(Call<List<OneCikanlarPojo>> call, Response<List<OneCikanlarPojo>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    oneCikanlarAdapter = new OneCikanlarAdapter(context, list, OneCikanlarActivity.this);
                    oneCikanlarActivityRecyclerView.setAdapter(oneCikanlarAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<OneCikanlarPojo>> call, Throwable t) {

            }
        });
    }
}
