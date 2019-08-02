package com.oguzzonall.e_emlakapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.oguzzonall.e_emlakapp.Models.IlanResimleriPojo;
import com.oguzzonall.e_emlakapp.R;
import com.oguzzonall.e_emlakapp.RestApi.BaseUrl;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EvDetaySliderAdapter extends PagerAdapter {
    List<IlanResimleriPojo> list;
    Context context;
    LayoutInflater layoutInflater;

    public EvDetaySliderAdapter(List<IlanResimleriPojo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.sliderlayout, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.sliderImageView);
        Picasso.with(context).load(BaseUrl.Url + list.get(position).getResimYolu()).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
