package com.oguzzonall.e_emlakapp.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oguzzonall.e_emlakapp.Adapters.MesajlarListAdapter;
import com.oguzzonall.e_emlakapp.Models.MessageModel;
import com.oguzzonall.e_emlakapp.R;

import java.util.ArrayList;
import java.util.List;

public class MesajlarimActivity extends AppCompatActivity {

    List<String> otherIdList;
    String userId;
    SharedPreferences sharedPreferences;
    DatabaseReference reference;
    MesajlarListAdapter mesajlarListAdapter;
    Context context = this;
    RecyclerView mesajlarim_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesajlarim);
        otherIdList = new ArrayList<>();
        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        userId = String.valueOf(sharedPreferences.getInt("uye_id", 0));
        reference = FirebaseDatabase.getInstance().getReference();
        mesajlarim_recyclerView=findViewById(R.id.mesajlarim_recyclerView);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MesajlarimActivity.this, 1);
        mesajlarim_recyclerView.setLayoutManager(layoutManager);
        mesajlarListAdapter=new MesajlarListAdapter(context,otherIdList,userId,MesajlarimActivity.this);
        mesajlarim_recyclerView.setAdapter(mesajlarListAdapter);

        listele();
    }

    public void listele() {
        reference.child("Mesajlar").child(userId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                otherIdList.add(dataSnapshot.getKey());
                mesajlarListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MessageModel m=dataSnapshot.getValue(MessageModel.class);
                otherIdList.remove(m);
                mesajlarListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
