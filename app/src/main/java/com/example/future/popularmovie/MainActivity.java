package com.example.future.popularmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements FilmFragment.Callback{

    boolean tab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("message", "welcome ");
        setContentView(R.layout.activity_main);

        if((findViewById(R.id.fragment))!=null){
            tab = true;
        }else{
            tab = false;
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.e("onCreateOptionsMenu", " mainactivity");

        return true;
    }


    @Override
    public void setAflam(aflamko aflam) {
        if(tab){
            DetailActivityFragment detailActivityFragment = DetailActivityFragment.newInstance(aflam);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,detailActivityFragment).commit();

        }else{
            Intent intent = new Intent(MainActivity.this, DetailActivity.class)
                    .putExtra("myfilm", aflam);
            startActivity(intent);
        }
    }
}
