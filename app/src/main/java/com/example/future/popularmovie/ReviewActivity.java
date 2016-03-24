package com.example.future.popularmovie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ReviewActivity extends AppCompatActivity {
public ArrayAdapter<String>ReviewAdapter;
    private ListView listView;
    ArrayList<Review> Revlist;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_review);

           Intent i =  getIntent();
          Revlist = i.getParcelableArrayListExtra("revList");


         listView= (ListView) findViewById(R.id.listReview);
        ReviewAdapter adapter= new ReviewAdapter();
        listView.setAdapter(adapter);

    }
    public class ReviewAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return Revlist.size();
        }

        @Override
        public Object getItem(int position) {
            return Revlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder= null;
            if (convertView==null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item_review, parent, false);
                holder = new ViewHolder();
                convertView.setTag(holder);
            }else {
            holder= (ViewHolder)convertView.getTag();
            }
            holder.tv= (TextView)convertView.findViewById(R.id.Review_content);
            holder.Tev= (TextView)convertView.findViewById(R.id.Review_outher);
            Review v= (Review) getItem(position);
            Log.i("log",v.getContent());
            holder.tv.setText(v.getContent());
            holder.Tev.setText(v.getOuther());
            return convertView;
        }


    }

    public class ViewHolder{
        TextView tv;
        TextView Tev;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_review, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
