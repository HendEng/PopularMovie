package com.example.future.popularmovie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TrailerActivity extends AppCompatActivity {
public ArrayAdapter TailerAdapter;
    ArrayList<Tailer> trList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        trList = getIntent().getParcelableArrayListExtra("trailerList");

        TailerAdapter adapter= new TailerAdapter();
        ListView lis = (ListView) findViewById(R.id.list_tailer);
        lis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tailer t = (Tailer) parent.getItemAtPosition(position);
                Intent i = new Intent(Intent.ACTION_VIEW , Uri.parse("https://www.youtube.com/watch?v="+t.getTailerID()));
                startActivity(i);
            }
        });
        lis.setAdapter(adapter);
    }

    public class TailerAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return trList.size();
        }

        @Override
        public Object getItem(int position) {
            return trList.get(position);
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
                convertView = inflater.inflate(R.layout.list_item_tailer, parent, false);
                holder = new ViewHolder();
                convertView.setTag(holder);
            }else {
                holder= (ViewHolder)convertView.getTag();
            }
            holder.tv= (TextView)convertView.findViewById(R.id.Tailer);
            Tailer v= trList.get(position);

            holder.tv.setText(v.getTailerName());

            return convertView;
        }
        public class ViewHolder{
            TextView tv;


        }

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
