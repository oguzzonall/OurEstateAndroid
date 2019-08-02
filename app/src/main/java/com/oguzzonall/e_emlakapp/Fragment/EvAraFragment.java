package com.oguzzonall.e_emlakapp.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.oguzzonall.e_emlakapp.Adapters.EvAramaSonucuAdapter;
import com.oguzzonall.e_emlakapp.Models.AramaSonucuIlanlarPojo;
import com.oguzzonall.e_emlakapp.R;
import com.oguzzonall.e_emlakapp.RestApi.ManagerAll;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EvAraFragment extends Fragment {
    Spinner evAraSehirlerSpinner, evAraIlcelerSpinner, evAraEmlakTipiSpinner;
    EditText evAraEnDusukFiyatEditText, evAraEnYuksekFiyatEditText;
    Button evAraEvBulButton;
    View view;
    String Sehir, Ilce, EmlakTipi;
    ArrayList<String> sehirlerList, ilcelerList, emlakTipiList;
    ArrayAdapter<String> adapter;
    List<AramaSonucuIlanlarPojo> list;
    ProgressDialog progress;
    RecyclerView evAraRecyclerView;
    EvAramaSonucuAdapter evAramaSonucuAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ev_ara, container, false);
        tanimla();
        action();
        return view;
    }

    public void tanimla() {
        evAraSehirlerSpinner = view.findViewById(R.id.evAraSehirlerSpinner);
        evAraIlcelerSpinner = view.findViewById(R.id.evAraIlcelerSpinner);
        evAraEmlakTipiSpinner = view.findViewById(R.id.evAraEmlakTipiSpinner);
        evAraEnDusukFiyatEditText = view.findViewById(R.id.evAraEnDusukFiyatEditText);
        evAraEnYuksekFiyatEditText = view.findViewById(R.id.evAraEnYuksekFiyatEditText);
        evAraEvBulButton = view.findViewById(R.id.evAraEvBulButton);
        evAraRecyclerView = view.findViewById(R.id.evAraRecyclerView);

        list = new ArrayList<>();
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(), 3);
        evAraRecyclerView.setLayoutManager(mng);

        sehirlerList = new ArrayList<>();
        ilcelerList = new ArrayList<>();
        emlakTipiList = new ArrayList<>();

        Sehir = "İstanbul";
        Ilce = "Fatih";
        EmlakTipi = "Apart";
    }

    public void action() {
        evAraSehirlerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("İstanbul")) {
                    ilcelerList.clear();
                    ilcelerList.add("Fatih");
                    ilcelerList.add("Pendik");
                    ilcelerList.add("Bağcılar");
                    ilcelerList.add("Ümraniye");
                    ilcelerList.add("Üskdar");
                    adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, ilcelerList);
                    evAraIlcelerSpinner.setAdapter(adapter);
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
                    adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, ilcelerList);
                    evAraIlcelerSpinner.setAdapter(adapter);
                }
                Sehir = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        evAraIlcelerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Ilce = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        evAraEmlakTipiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EmlakTipi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        evAraEvBulButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evAraEnDusukFiyatEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "En Düşük Fiyat Aralığı Boş Bırakılamaz", Toast.LENGTH_SHORT).show();
                    return;
                } else if (evAraEnYuksekFiyatEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), "En Yüksek Fiyat Aralığı Boş Bırakılamaz", Toast.LENGTH_SHORT).show();
                    return;
                } else if (evAraEnYuksekFiyatEditText.getText().toString().trim().equals(".") || evAraEnDusukFiyatEditText.getText().toString().trim().equals(".")) {
                    Toast.makeText(getContext(), "Lütfen Sayısal Bir İfade Giriniz.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (Integer.valueOf(evAraEnDusukFiyatEditText.getText().toString()) > Integer.valueOf(evAraEnYuksekFiyatEditText.getText().toString())) {
                    Toast.makeText(getContext(), "En Yüksek Fiyat Aralığı En Düşük Fiyat Aralığından Küçük Olamaz.", Toast.LENGTH_SHORT).show();
                } else {
                    int enDusukFiyat = Integer.valueOf(evAraEnDusukFiyatEditText.getText().toString());
                    int enYusukFiyat = Integer.valueOf(evAraEnYuksekFiyatEditText.getText().toString());
                    getAramaSonucuIlanlar(Sehir, Ilce, EmlakTipi, enDusukFiyat, enYusukFiyat);
                }
            }
        });
    }

    public void getAramaSonucuIlanlar(String sehir, String ilce, String emlakTipi, int enDusukFiyat, int enYuksekFiyat) {
        Call<List<AramaSonucuIlanlarPojo>> request = ManagerAll.getGetInstanse().GetAramaSonucuIlanlar(sehir, ilce, emlakTipi, enDusukFiyat, enYuksekFiyat);
        request.enqueue(new Callback<List<AramaSonucuIlanlarPojo>>() {
            @Override
            public void onResponse(Call<List<AramaSonucuIlanlarPojo>> call, Response<List<AramaSonucuIlanlarPojo>> response) {
                progress = new ProgressDialog(getContext());
                progress.setMessage("Arama İşleminiz Gerçekleştiriliyor.");
                progress.show();
                if (response.isSuccessful()) {
                    if (response.body().get(0).isSonuc()) {
                        list = response.body();
                        evAramaSonucuAdapter = new EvAramaSonucuAdapter(getContext(), list, getActivity());
                        evAraRecyclerView.setAdapter(evAramaSonucuAdapter);
                        progress.cancel();
                    } else {
                        progress.cancel();
                        Toast.makeText(getContext(), response.body().get(0).getAciklama(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progress.cancel();
                    Toast.makeText(getContext(), "İşleminiz Gerçekleştirilemedi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AramaSonucuIlanlarPojo>> call, Throwable t) {
                Toast.makeText(getContext(), "Lütfen İnternet Bağlantınızı Kontrol Ediniz.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
