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
import com.oguzzonall.e_emlakapp.Models.EnCokZiyaretEdilenlerPojo;
import com.oguzzonall.e_emlakapp.Models.OneCikanlarPojo;
import com.oguzzonall.e_emlakapp.R;
import com.oguzzonall.e_emlakapp.RestApi.BaseUrl;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OneCikanlarAdapter extends RecyclerView.Adapter<OneCikanlarAdapter.ViewHolder> {
    Context context;
    List<OneCikanlarPojo> list;
    Activity activity;

    public OneCikanlarAdapter(Context context, List<OneCikanlarPojo> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @Override
    public OneCikanlarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.onecikanlarlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OneCikanlarAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.oneCikanlarTextViewAdapter.setText(list.get(position).getIlanBaslik());
        viewHolder.oneCikanlarButtonAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EvDetayActivity.class);
                intent.putExtra("ilanId", list.get(position).getIlanId());
                activity.startActivity(intent);
            }
        });
        Picasso.with(context).load(BaseUrl.Url + list.get(position).getResimYolu()).into(viewHolder.oneCikanlarImageViewAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView oneCikanlarTextViewAdapter;
        ImageView oneCikanlarImageViewAdapter;
        Button oneCikanlarButtonAdapter;

        ViewHolder(View itemView) {
            super(itemView);
            oneCikanlarTextViewAdapter = itemView.findViewById(R.id.oneCikanlarTextViewAdapter);
            oneCikanlarImageViewAdapter = itemView.findViewById(R.id.oneCikanlarImageViewAdapter);
            oneCikanlarButtonAdapter = itemView.findViewById(R.id.oneCikanlarButtonAdapter);

        }
    }
}
