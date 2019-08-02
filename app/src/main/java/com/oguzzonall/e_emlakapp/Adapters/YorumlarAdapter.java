package com.oguzzonall.e_emlakapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oguzzonall.e_emlakapp.Models.YorumlarPojo;
import com.oguzzonall.e_emlakapp.R;

import java.util.List;

public class YorumlarAdapter extends RecyclerView.Adapter<YorumlarAdapter.ViewHolder> {
    Context context;
    List<YorumlarPojo> list;

    public YorumlarAdapter(Context context, List<YorumlarPojo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.yorumlarlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.yorumlarlayoutIsimSoyasimTextview.setText(list.get(position).getKullaniciAdi());
        viewHolder.yorumlarlayoutBaslikTextview.setText(list.get(position).getBaslik());
        viewHolder.yorumlarlayoutTarihTextview.setText(list.get(position).getTarih());
        viewHolder.yorumlarlayoutIcerikTextview.setText(list.get(position).getIcerik());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView yorumlarlayoutIsimSoyasimTextview, yorumlarlayoutTarihTextview, yorumlarlayoutBaslikTextview, yorumlarlayoutIcerikTextview;

        ViewHolder(View itemView) {
            super(itemView);
            yorumlarlayoutIsimSoyasimTextview = itemView.findViewById(R.id.yorumlarlayoutIsimSoyasimTextview);
            yorumlarlayoutTarihTextview = itemView.findViewById(R.id.yorumlarlayoutTarihTextview);
            yorumlarlayoutBaslikTextview = itemView.findViewById(R.id.yorumlarlayoutBaslikTextview);
            yorumlarlayoutIcerikTextview = itemView.findViewById(R.id.yorumlarlayoutIcerikTextview);

        }

    }
}
