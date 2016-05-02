package edu.otc.rileyre.popularmoviesapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Movie;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by rileyre on 4/30/2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    final Random random = new Random();
    private List<OurMovie> urls = Collections.synchronizedList(new ArrayList<OurMovie>());


    public ImageAdapter(Context m) { mContext = m; }

    public int getCount() { return urls.size(); }

    public OurMovie getItem(int position) { return urls.get(position); }

    @Override
    public long getItemId(int position) { return position%2; }

    public void updateURLS(List<OurMovie> urls) {
        this.urls.clear();
        this.urls.addAll(urls);
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(185, 277));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setBackgroundColor(Color.BLACK);
        } else {
            imageView = (ImageView) convertView;
        }

        final  Picasso picaso = Picasso.with(mContext);
        picaso.load(getItem(position).posterUrl).fit().into(imageView);

        return imageView;
    }


}
