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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oguzzonall.e_emlakapp.Activity.EvDetayActivity;
import com.oguzzonall.e_emlakapp.Models.EnCokZiyaretEdilenlerPojo;
import com.oguzzonall.e_emlakapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EnCokZiyaretEdilenlerAdapter extends RecyclerView.Adapter<EnCokZiyaretEdilenlerAdapter.ViewHolder> {
    Context context;
    List<EnCokZiyaretEdilenlerPojo> list;
    Activity activity;

    public EnCokZiyaretEdilenlerAdapter(Context context, List<EnCokZiyaretEdilenlerPojo> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.encokziyaretedilenlerlayout, parent, false);
        return new EnCokZiyaretEdilenlerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.enCokZiyaretEdilenlerButtonAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EvDetayActivity.class);
                intent.putExtra("ilanId", list.get(position).getIlanId());
                activity.startActivity(intent);
            }
        });
        viewHolder.enCokZiyaretEdilenlerTextViewAdapter.setText(list.get(position).getIlanBaslik());
        Picasso.with(context).load("http://emlakmobil.azurewebsites.net" + list.get(position).getResimYolu()).into(viewHolder.enCokZiyaretEdilenlerImageViewAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //Viewların tanımlanam işlemleri yapılacak.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView enCokZiyaretEdilenlerTextViewAdapter;
        ImageView enCokZiyaretEdilenlerImageViewAdapter;
        Button enCokZiyaretEdilenlerButtonAdapter;

        ViewHolder(View itemView) {
            super(itemView);
            enCokZiyaretEdilenlerTextViewAdapter = itemView.findViewById(R.id.enCokZiyaretEdilenlerTextViewAdapter);
            enCokZiyaretEdilenlerImageViewAdapter = itemView.findViewById(R.id.enCokZiyaretEdilenlerImageViewAdapter);
            enCokZiyaretEdilenlerButtonAdapter = itemView.findViewById(R.id.enCokZiyaretEdilenlerButtonAdapter);

        }

    }
}
