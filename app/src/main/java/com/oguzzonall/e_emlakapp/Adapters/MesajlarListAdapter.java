package com.oguzzonall.e_emlakapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oguzzonall.e_emlakapp.Activity.ChatActivity;
import com.oguzzonall.e_emlakapp.Models.UserPojo;
import com.oguzzonall.e_emlakapp.R;
import com.oguzzonall.e_emlakapp.RestApi.ManagerAll;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MesajlarListAdapter extends RecyclerView.Adapter<MesajlarListAdapter.ViewHolder> {

    Context context;
    List<String> otherIdList;
    String userId;
    Activity activity;
    List<String> otherNameList;

    public MesajlarListAdapter(Context context, List<String> otherIdList, String userId, Activity activity) {
        this.context = context;
        this.otherIdList = otherIdList;
        this.userId = userId;
        this.activity = activity;
        otherNameList = new ArrayList<>();
    }

    @Override
    public MesajlarListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.mesajlastiklarim, parent, false);
        return new ViewHolder(view);
    }

    //Viewlara setleme
    @Override
    public void onBindViewHolder(@NonNull MesajlarListAdapter.ViewHolder viewHolder, final int position) {
        istekAt(Integer.valueOf(otherIdList.get(position)), viewHolder.friend_req_TextView);
        viewHolder.mesajlastiklarimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ChatActivity.class);
                intent.putExtra("otherId", otherIdList.get(position));
                intent.putExtra("username", otherNameList.get(position));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return otherIdList.size();
    }

    //Viewların tanımlanam işlemleri yapılacak.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView friend_req_TextView;
        Button mesajlastiklarimButton;

        ViewHolder(View itemView) {
            super(itemView);
            friend_req_TextView = itemView.findViewById(R.id.mesajlastiklarimTextView);
            mesajlastiklarimButton = itemView.findViewById(R.id.mesajlastiklarimButton);
        }
    }

    public void istekAt(int uye_id, final TextView textView) {
        Call<UserPojo> request = ManagerAll.getGetInstanse().GetAdSoyadById(uye_id);
        request.enqueue(new Callback<UserPojo>() {
            @Override
            public void onResponse(Call<UserPojo> call, Response<UserPojo> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSonuc()) {
                        textView.setText(response.body().getAdSoyad());
                        otherNameList.add(response.body().getAdSoyad());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserPojo> call, Throwable t) {

            }
        });
    }
}
