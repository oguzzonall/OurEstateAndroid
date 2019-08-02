package com.oguzzonall.e_emlakapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.oguzzonall.e_emlakapp.Adapters.AnaSliderAdapter;
import com.oguzzonall.e_emlakapp.Adapters.EnCokZiyaretEdilenlerAdapter;
import com.oguzzonall.e_emlakapp.Adapters.MesajAdapter;
import com.oguzzonall.e_emlakapp.Adapters.OneCikanlarAdapter;
import com.oguzzonall.e_emlakapp.Fragment.AnaSayfaFragment;
import com.oguzzonall.e_emlakapp.Fragment.EvAraFragment;
import com.oguzzonall.e_emlakapp.Fragment.HakkimdaFragment;
import com.oguzzonall.e_emlakapp.Fragment.IletisimFragment;
import com.oguzzonall.e_emlakapp.Models.EnCokZiyaretEdilenlerPojo;
import com.oguzzonall.e_emlakapp.Models.MessageModel;
import com.oguzzonall.e_emlakapp.Models.OneCikanlarPojo;
import com.oguzzonall.e_emlakapp.Models.RolsPojo;
import com.oguzzonall.e_emlakapp.R;
import com.oguzzonall.e_emlakapp.RestApi.ManagerAll;
import com.oguzzonall.e_emlakapp.Utils.ChangeFragment;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context = this;
    private ChangeFragment changeFragment;
    SharedPreferences sharedPreferences;
    String navHeaderText;
    TextView navHeaderTextView, enCokZiyaretEdilenlerTextview, oneCikanlarContentMainTextView;
    NavigationView navigationView;
    SharedPreferences.Editor editor;
    RecyclerView enCokZiyaretEdilenlerTop3RecyclerView, OneCikanlarTop3_recyclerView;
    int uyeid;
    List<EnCokZiyaretEdilenlerPojo> enCokZiyaretEdilenlerList;
    List<OneCikanlarPojo> oneCikanlarList;
    List<RolsPojo> rolsPojoList;
    EnCokZiyaretEdilenlerAdapter enCokZiyaretEdilenlerAdapter;
    OneCikanlarAdapter oneCikanlarAdapter;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Anasayfa Yükleniyor...");
        progressDialog.show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        navHeaderText = sharedPreferences.getString("uye_KullaniciAdi", null);
        uyeid = sharedPreferences.getInt("uye_id", 0);

    /*    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (navHeaderText == null || uyeid == 0) {
            navigationView.getMenu().findItem(R.id.nav_mesajlarim).setVisible(false);
        }

        getRolsById(uyeid);


        View headerView = navigationView.getHeaderView(0);
        navHeaderTextView = headerView.findViewById(R.id.textView);
        navHeaderTextView.setText(navHeaderText);

        changeFragment = new ChangeFragment(context);
        changeFragment.change(new AnaSayfaFragment());
        tanimla();
        getEnCokZiyaretEdilenlerTop3();
        getOneCikanlarTop3();
        progressDialog.cancel();

    }


    public void tanimla() {
        enCokZiyaretEdilenlerTextview = findViewById(R.id.enCokZiyaretEdilenlerContentMainTextview);
        oneCikanlarContentMainTextView = findViewById(R.id.oneCikanlarContentMainTextView);
        enCokZiyaretEdilenlerTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EnCokZiyaretEdilenlerActivity.class);
                startActivity(intent);
            }
        });
        oneCikanlarContentMainTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OneCikanlarActivity.class);
                startActivity(intent);
            }
        });
        OneCikanlarTop3_recyclerView = findViewById(R.id.OneCikanlarRecyclerView);
        enCokZiyaretEdilenlerTop3RecyclerView = findViewById(R.id.enCokZiyaretEdilenlerRecyclerView);
        enCokZiyaretEdilenlerList = new ArrayList<>();
        oneCikanlarList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 3);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(MainActivity.this, 3);
        enCokZiyaretEdilenlerTop3RecyclerView.setLayoutManager(layoutManager);
        OneCikanlarTop3_recyclerView.setLayoutManager(layoutManager1);

    }

    public void getRolsById(int id) {
        rolsPojoList = new ArrayList<>();
        Call<List<RolsPojo>> request = ManagerAll.getGetInstanse().GetRolsById(id);
        request.enqueue(new Callback<List<RolsPojo>>() {
            @Override
            public void onResponse(Call<List<RolsPojo>> call, Response<List<RolsPojo>> response) {
                if (response.isSuccessful()) {
                    rolsPojoList = response.body();
                    for (int i = 0; i < rolsPojoList.size(); i++) {
                        if (rolsPojoList.get(i).getRols().equals("Musteri")) {
                            navigationView.getMenu().findItem(R.id.nav_mesajlarim).setVisible(true);
                            navigationView.getMenu().findItem(R.id.nav_cikisYap).setVisible(true);
                            navigationView.getMenu().findItem(R.id.nav_kayit).setVisible(false);
                            navigationView.getMenu().findItem(R.id.nav_giris).setVisible(false);
                        } else if (rolsPojoList.get(i).getRols().equals("Satici")) {
                            navigationView.getMenu().findItem(R.id.nav_mesajlarim).setVisible(true);
                            navigationView.getMenu().findItem(R.id.nav_ilanver).setVisible(true);
                            navigationView.getMenu().findItem(R.id.nav_cikisYap).setVisible(true);
                            navigationView.getMenu().findItem(R.id.nav_kayit).setVisible(false);
                            navigationView.getMenu().findItem(R.id.nav_giris).setVisible(false);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RolsPojo>> call, Throwable t) {

            }
        });
    }

    public void getEnCokZiyaretEdilenlerTop3() {
        Call<List<EnCokZiyaretEdilenlerPojo>> request = ManagerAll.getGetInstanse().GetEnCokZiyaretEdilenlerTop3();
        request.enqueue(new Callback<List<EnCokZiyaretEdilenlerPojo>>() {
            @Override
            public void onResponse(Call<List<EnCokZiyaretEdilenlerPojo>> call, Response<List<EnCokZiyaretEdilenlerPojo>> response) {
                if (response.isSuccessful()) {
                    enCokZiyaretEdilenlerList = response.body();
                    enCokZiyaretEdilenlerAdapter = new EnCokZiyaretEdilenlerAdapter(context, enCokZiyaretEdilenlerList, MainActivity.this);
                    enCokZiyaretEdilenlerTop3RecyclerView.setAdapter(enCokZiyaretEdilenlerAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<EnCokZiyaretEdilenlerPojo>> call, Throwable t) {

            }
        });
    }

    public void getOneCikanlarTop3() {
        Call<List<OneCikanlarPojo>> request = ManagerAll.getGetInstanse().GetFavoriIlanlarTop3();
        request.enqueue(new Callback<List<OneCikanlarPojo>>() {
            @Override
            public void onResponse(Call<List<OneCikanlarPojo>> call, Response<List<OneCikanlarPojo>> response) {
                if (response.isSuccessful()) {
                    oneCikanlarList = response.body();
                    oneCikanlarAdapter = new OneCikanlarAdapter(context, oneCikanlarList, MainActivity.this);
                    OneCikanlarTop3_recyclerView.setAdapter(oneCikanlarAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<OneCikanlarPojo>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_anasayfa) {
            changeFragment.change(new AnaSayfaFragment());
        } else if (id == R.id.nav_hakkinda) {
            changeFragment.change(new HakkimdaFragment());
        } else if (id == R.id.nav_iletisim) {
            changeFragment.change(new IletisimFragment());
        } else if (id == R.id.nav_evbul) {
            changeFragment.change(new EvAraFragment());
        } else if (id == R.id.nav_ilanver) {
            Intent intent = new Intent(this, IlanVerActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_giris) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_kayit) {
            Intent intent = new Intent(this, KayitOlActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_mesajlarim) {
            Intent intent = new Intent(this, MesajlarimActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_cikisYap) {
            editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
