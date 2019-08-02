package com.oguzzonall.e_emlakapp.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.oguzzonall.e_emlakapp.Adapters.YorumlarAdapter;
import com.oguzzonall.e_emlakapp.Models.YorumlarPojo;
import com.oguzzonall.e_emlakapp.R;
import com.oguzzonall.e_emlakapp.RestApi.ManagerAll;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TumYorumlarActivity extends AppCompatActivity {
    int ilanId;
    Context context = this;
    YorumlarAdapter tumyorumlarAdapter;
    RecyclerView tumYorumlarActivityRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tum_yorumlar);
        Bundle bundle = getIntent().getExtras();
        ilanId = bundle.getInt("ilanId");
        tanimla();
        GetYorumlarById(ilanId);
    }

    public void tanimla() {
        tumYorumlarActivityRecyclerView = findViewById(R.id.tumYorumlarActivityRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(TumYorumlarActivity.this, 1);
        tumYorumlarActivityRecyclerView.setLayoutManager(layoutManager);
    }

    public void GetYorumlarById(int ilanId) {
        Call<List<YorumlarPojo>> request = ManagerAll.getGetInstanse().GetYorumlarById(ilanId);
        request.enqueue(new Callback<List<YorumlarPojo>>() {
            @Override
            public void onResponse(Call<List<YorumlarPojo>> call, Response<List<YorumlarPojo>> response) {
                if (response.isSuccessful()) {
                    Log.i("wadwdwadw",response.body().toString());
                    if (response.body().get(0).Sonuc) {
                        tumyorumlarAdapter = new YorumlarAdapter(context, response.body());
                        tumYorumlarActivityRecyclerView.setAdapter(tumyorumlarAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<YorumlarPojo>> call, Throwable t) {

            }
        });

    }
}
