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
import android.widget.ImageView;
import android.widget.TextView;

import com.oguzzonall.e_emlakapp.Activity.EvDetayActivity;
import com.oguzzonall.e_emlakapp.Models.AramaSonucuIlanlarPojo;
import com.oguzzonall.e_emlakapp.R;
import com.oguzzonall.e_emlakapp.RestApi.BaseUrl;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EvAramaSonucuAdapter extends RecyclerView.Adapter<EvAramaSonucuAdapter.ViewHolder> {
    Context context;
    List<AramaSonucuIlanlarPojo> list;
    Activity activity;

    public EvAramaSonucuAdapter(Context context, List<AramaSonucuIlanlarPojo> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @Override
    public EvAramaSonucuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.evaramasonuculayout, parent, false);
        return new EvAramaSonucuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EvAramaSonucuAdapter.ViewHolder viewHolder, int position) {
        viewHolder.evAramaSonucuTextViewAdapter.setText(list.get(position).getIlanBaslik());
        viewHolder.evAramaSonucuButtonAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EvDetayActivity.class);
                intent.putExtra("ilanId", list.get(0).getIlanId());
                activity.startActivity(intent);
            }
        });
        Picasso.with(context).load(BaseUrl.Url + list.get(position).getResimYolu()).into(viewHolder.evAramaSonucuImageViewAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView evAramaSonucuTextViewAdapter;
        ImageView evAramaSonucuImageViewAdapter;
        Button evAramaSonucuButtonAdapter;

        ViewHolder(View itemView) {
            super(itemView);
            evAramaSonucuTextViewAdapter = itemView.findViewById(R.id.evAramaSonucuTextViewAdapter);
            evAramaSonucuImageViewAdapter = itemView.findViewById(R.id.evAramaSonucuImageViewAdapter);
            evAramaSonucuButtonAdapter = itemView.findViewById(R.id.evAramaSonucuButtonAdapter);

        }

    }
}
