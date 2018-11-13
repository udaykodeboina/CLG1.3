package com.church.clg.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.church.clg.R;

public class GalleryAdapter  extends BaseAdapter {
    Context context;

    int flags[];
    LayoutInflater inflter;

    public GalleryAdapter(Context applicationContext, int[] flags) {
        this.context = context;

        this.flags = flags;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return flags.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_listview, null);

        ImageView icon = (ImageView) view.findViewById(R.id.icon);

        icon.setImageResource(flags[i]);
        return view;
    }
}