package com.example.future.popularmovie;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
/**
 * Created by future on 21/12/2015.
 */
public class Custom_adapter extends BaseAdapter {
    Context context;
    ArrayList<aflamko> movie;
    public Custom_adapter(Context context, ArrayList<aflamko> movie) {
        this.context = context;
        this.movie = movie;

    }
    @Override
    public int getCount() {

        return movie.size();
    }
    @Override
    public long getItemId(int position) {

        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



            View v = inflater.inflate(R.layout.list_item_film, parent, false);
          ImageView iv= (ImageView) v.findViewById(R.id.imageView_film);
            v.setTag(iv);

            aflamko aflam = getItem(position);


            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/w185" + aflam.getPoster())
                    .into(iv);

        return v;
    }
    @Override
    public aflamko getItem(int i) {

        return movie.get(i);
    }
}
