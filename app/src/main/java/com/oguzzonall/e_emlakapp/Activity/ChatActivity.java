package com.oguzzonall.e_emlakapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oguzzonall.e_emlakapp.Adapters.MesajAdapter;
import com.oguzzonall.e_emlakapp.Models.MessageModel;
import com.oguzzonall.e_emlakapp.R;
import com.oguzzonall.e_emlakapp.Utils.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChatActivity extends AppCompatActivity {
    Context context = this;
    ImageView chatBackImageView;
    TextView chatOtherNameTextview;
    EditText chat_EditText;
    Button chat_button;
    String userId, otherId;
    SharedPreferences sharedPreferences;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    List<MessageModel> list;
    MesajAdapter messageAdapter;
    RecyclerView chat_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tanimla();
        action();
        loadMessage();
    }


    public void tanimla() {
        sharedPreferences = getApplicationContext().getSharedPreferences("giris", 0);
        chatBackImageView = findViewById(R.id.chatBackImageView);
        chatOtherNameTextview = findViewById(R.id.chatOtherNameTextview);
        chat_EditText = findViewById(R.id.chat_EditText);
        chat_button = findViewById(R.id.chat_button);
        chatOtherNameTextview.setText(getUserName());

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        otherId = getOtherId();
        userId = getUserId();

        list = new ArrayList<>();
        chat_recyclerView = findViewById(R.id.chat_recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ChatActivity.this, 1);
        chat_recyclerView.setLayoutManager(layoutManager);
        messageAdapter = new MesajAdapter(ChatActivity.this, context, userId, otherId, list);
        chat_recyclerView.setAdapter(messageAdapter);
    }

    public void action() {
        chat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = chat_EditText.getText().toString().trim();
                if (!message.equals("")) {
                    sendMessage(getUserId(), getOtherId(), "text", DateTime.getTime(), false, message);
                    chat_EditText.setText("");
                }
            }
        });
        chatBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, MesajlarimActivity.class);
                startActivity(intent);
                finish();
            }
        });
        chatOtherNameTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public String getUserId() {
        String uyeId = String.valueOf(sharedPreferences.getInt("uye_id", 0));
        return uyeId;
    }

    public String getOtherId() {
        String otherId = getIntent().getExtras().getString("otherId");
        return otherId;
    }

    public String getUserName() {
        String username = getIntent().getExtras().getString("username");
        return username;
    }

    public void sendMessage(final String userId, final String otherId, String textType, String date, Boolean seen, String messageText) {
        final String mesajId = reference.child("Mesajlar").child(userId).child(otherId).push().getKey();
        final Map mesajMap = new HashMap();

        mesajMap.put("type", textType);
        mesajMap.put("seen", seen);
        mesajMap.put("time", date);
        mesajMap.put("text", messageText);
        mesajMap.put("from", userId);

        reference.child("Mesajlar").child(userId).child(otherId).child(mesajId).setValue(mesajMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    reference.child("Mesajlar").child(otherId).child(userId).child(mesajId).setValue(mesajMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }
            }
        });
    }

    public void loadMessage() {
        reference.child("Mesajlar").child(getOtherId()).child(getUserId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    MessageModel message = dataSnapshot.getValue(MessageModel.class);
                    if (message.getSeen() == false) {
                        reference.child("Mesajlar").child(getOtherId()).child(getUserId()).child(dataSnapshot.getKey()).child("seen").setValue(true);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        reference.child("Mesajlar").child(getUserId()).child(getOtherId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                list.add(messageModel);
                messageAdapter.notifyDataSetChanged();
                chat_recyclerView.scrollToPosition(list.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                messageAdapter.notifyDataSetChanged();
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
