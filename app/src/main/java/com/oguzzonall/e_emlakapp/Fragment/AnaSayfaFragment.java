package com.oguzzonall.e_emlakapp.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzzonall.e_emlakapp.Activity.MainActivity;
import com.oguzzonall.e_emlakapp.Adapters.AnaSliderAdapter;
import com.oguzzonall.e_emlakapp.Adapters.OneCikanlarAdapter;
import com.oguzzonall.e_emlakapp.Adapters.UygunFiyatlarAdapter;
import com.oguzzonall.e_emlakapp.Models.EnUygunFiyatlarPojo;
import com.oguzzonall.e_emlakapp.Models.OneCikanlarPojo;
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

public class AnaSayfaFragment extends Fragment {
    View view;
    ViewPager mainActivityAnaSlider;
    TextView uygunFiyatlarTextview;
    CircleIndicator mainActivityAnaSliderCircle;
    AnaSliderAdapter mainActivityAnaSliderAdapter;
    List<Integer> list;
    List<EnUygunFiyatlarPojo> enUygunFiyatlarPojoList;
    RecyclerView UygunFiyatlarRecyclerView;
    UygunFiyatlarAdapter uygunFiyatlarAdapter;
    Timer timer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ana_sayfa, container, false);
        tanimla();
        getUygunFiyatlarTop3();
        return view;
    }

    @Override
    public void onPause() {
        timer.cancel();
        super.onPause();
    }


    public void tanimla() {
        uygunFiyatlarTextview = view.findViewById(R.id.uygunFiyatlarTextview);

        mainActivityAnaSlider = view.findViewById(R.id.mainActivityAnaSlider);
        mainActivityAnaSliderCircle = view.findViewById(R.id.mainActivityAnaSliderCircle);
        UygunFiyatlarRecyclerView = view.findViewById(R.id.UygunFiyatlarRecyclerView);
        list = new ArrayList<>();
        enUygunFiyatlarPojoList = new ArrayList<>();
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(), 3);
        UygunFiyatlarRecyclerView.setLayoutManager(mng);


        list.add(R.mipmap.image);
        list.add(R.mipmap.eemlaklogolong);
        list.add(R.mipmap.image);

        mainActivityAnaSliderAdapter = new AnaSliderAdapter(list, getContext(), getActivity());
        mainActivityAnaSlider.setAdapter(mainActivityAnaSliderAdapter);
        mainActivityAnaSliderCircle.setViewPager(mainActivityAnaSlider);
        mainActivityAnaSliderCircle.bringToFront();

        timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
    }

    public void getUygunFiyatlarTop3() {
        Call<List<EnUygunFiyatlarPojo>> request = ManagerAll.getGetInstanse().GetEnUygunFiyatlarTop3();
        request.enqueue(new Callback<List<EnUygunFiyatlarPojo>>() {
            @Override
            public void onResponse(Call<List<EnUygunFiyatlarPojo>> call, Response<List<EnUygunFiyatlarPojo>> response) {
                if (response.isSuccessful()) {
                    enUygunFiyatlarPojoList = response.body();
                    uygunFiyatlarAdapter = new UygunFiyatlarAdapter(getContext(), enUygunFiyatlarPojoList, getActivity());
                    UygunFiyatlarRecyclerView.setAdapter(uygunFiyatlarAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<EnUygunFiyatlarPojo>> call, Throwable t) {

            }
        });
    }

    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mainActivityAnaSlider.getCurrentItem() != mainActivityAnaSlider.getOffscreenPageLimit() + 1) {
                        mainActivityAnaSlider.setCurrentItem(mainActivityAnaSlider.getCurrentItem() + 1);
                    } else {
                        mainActivityAnaSlider.setCurrentItem(0);
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
