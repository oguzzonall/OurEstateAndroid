package com.oguzzonall.e_emlakapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oguzzonall.e_emlakapp.Activity.EvDetayActivity;
import com.oguzzonall.e_emlakapp.Models.EnUygunFiyatlarPojo;
import com.oguzzonall.e_emlakapp.R;
import com.oguzzonall.e_emlakapp.RestApi.BaseUrl;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UygunFiyatlarAdapter extends RecyclerView.Adapter<UygunFiyatlarAdapter.ViewHolder> {
    Context context;
    List<EnUygunFiyatlarPojo> list;
    Activity activity;

    public UygunFiyatlarAdapter(Context context, List<EnUygunFiyatlarPojo> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.uygunfiyatlarlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        viewHolder.uygunFiyatlarButtonAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EvDetayActivity.class);
                intent.putExtra("ilanId", list.get(position).getIlanId());
                activity.startActivity(intent);
            }
        });
        viewHolder.uygunFiyatlarTextViewAdapter.setText(list.get(position).getIlanBaslik());
        Picasso.with(context).load(BaseUrl.Url + list.get(position).getResimYolu()).into(viewHolder.uygunFiyatlarImageViewAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //Viewların tanımlama işlemleri yapılacak.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView uygunFiyatlarTextViewAdapter;
        ImageView uygunFiyatlarImageViewAdapter;
        Button uygunFiyatlarButtonAdapter;

        ViewHolder(View itemView) {
            super(itemView);
            uygunFiyatlarTextViewAdapter = itemView.findViewById(R.id.uygunFiyatlarTextViewAdapter);
            uygunFiyatlarImageViewAdapter = itemView.findViewById(R.id.uygunFiyatlarImageViewAdapter);
            uygunFiyatlarButtonAdapter = itemView.findViewById(R.id.uygunFiyatlarButtonAdapter
            );

        }

    }
}
