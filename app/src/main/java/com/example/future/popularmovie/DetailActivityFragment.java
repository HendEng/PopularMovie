package com.example.future.popularmovie;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements View.OnClickListener {
    ArrayList moviehd;
    Context context;
     aflamko aflamko;
      Tailer Tailer;
     Review Review;
    private TextView title;
    private GridView gridview;
    private  TextView vote;
    private TextView view;
    private TextView release;
    private ImageView postert;
    private Button displayReview,displayTrailer;
private Button addtovefar;
    public DetailActivityFragment() {
    }

    public static DetailActivityFragment newInstance(aflamko aflamko) {
        DetailActivityFragment frg = new DetailActivityFragment();
        Bundle args = new Bundle();
        args.putParcelable("aflam", aflamko);
        frg.setArguments(args);
        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View rootview = inflater.inflate(R.layout.fragment_detail, container, false);
         title = (TextView) rootview.findViewById(R.id.title);
         vote = (TextView)rootview.findViewById(R.id.vote_avreage);
         release= (TextView)rootview.findViewById(R.id.Release_data);
         postert= (ImageView)rootview.findViewById(R.id.imageView_detail);
         view= (TextView)rootview.findViewById(R.id.View);
         displayReview = (Button) rootview.findViewById(R.id.review);
         displayTrailer= (Button) rootview.findViewById(R.id.trailer);
         addtovefar= (Button)rootview.findViewById(R.id.faver);
         displayReview.setOnClickListener(this);
         displayTrailer.setOnClickListener(this);
         addtovefar.setOnClickListener(this);
         return rootview;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aflamko = getArguments().getParcelable("aflam");
        title.setText(aflamko.getTitle());
        vote.setText(aflamko.getVote());
        view.setText(aflamko.getView());
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185" + aflamko.getPoster())
                .into(postert);
        Log.i("postert", postert.toString());
        release.setText(aflamko.getRelease());
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.review:
                fetchReview();
                break;
            case R.id.trailer:
                fetchTrailer();
                break;
            case R.id.faver:




                InsertData(aflamko);
                Toast.makeText(getActivity(), " Add to favorite ", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public boolean InsertData (aflamko films) {
        boolean createsuccess= false;
        ContentValues values = new ContentValues();
        values.put(MovieDbHelper.COLUMN_NAME_ID, films.getID());
        values.put(MovieDbHelper.COLUMN_NAME_poster, films.getPoster());
        values.put(MovieDbHelper.COLUMN_NAME_release_data, films.getRelease());
        values.put(MovieDbHelper.COLUMN_NAME_vote_average, films.getVote());
        values.put(MovieDbHelper.COLUMN_NAME_title, films.getTitle());
        values.put(MovieDbHelper.COLUMN_NAME_overview, films.getView());

        MovieDbHelper DPhelperOBJ= new MovieDbHelper(getActivity());

        SQLiteDatabase db = DPhelperOBJ.getWritableDatabase();
        createsuccess = db.insert(MovieDbHelper.TABLE_NAME, null, values) > 0;
        db.close();

        return createsuccess;
    }

        public class TailerFetch extends AsyncTask<Void, Void, ArrayList<Tailer>> {
        private final String LOG_TAG = TailerFetch.class.getSimpleName();
        @Override
        protected ArrayList doInBackground(Void... params) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;
            String TailerJson = null;
            final String BaseURL = ("http://api.themoviedb.org/3/movie/");
            Uri BuildUri = Uri.parse(BaseURL).buildUpon()
                    .appendPath(aflamko.getID())
                    .appendPath("videos")
                    .appendQueryParameter("api_key", "944d2e37b62a28829abd7c15058021fb")
                    .build();
            URL url = null;
            try {
                url = new URL(BuildUri.toString());
                Log.i("THEURL", BuildUri.toString());
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line + "/n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                TailerJson = buffer.toString();
                Log.i("**************", TailerJson);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (final IOException e) {
                    }
                }
            }
            try {
                return getIDdatafromJSON(TailerJson);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        public ArrayList<Tailer> getIDdatafromJSON(String TailerJson)
                throws JSONException {
            final String list = "results";
            String ID = "id";
            JSONObject movieJson = null;
            String[] resultstrs = null;
            moviehd = new ArrayList<>();
            try {
                movieJson = new JSONObject(TailerJson);
                JSONArray moviearray = movieJson.getJSONArray(list);
                resultstrs = new String[moviearray.length()];
                for (int i = 0; i < moviearray.length(); i++) {
                    JSONObject movie_data = moviearray.getJSONObject(i);
                    ID = movie_data.getString("key");
                    String name = movie_data.getString("name");
                    Log.i("&&&&&&&&&&&&&&&", ID);
                    moviehd.add(new Tailer(ID,name));
                }
            } catch (JSONException e) {
                Log.i("moviehd", moviehd.toString());
                {
                }
            }
            return moviehd;
        }
        @Override
        protected void onPostExecute(ArrayList<Tailer> res) {

            super.onPostExecute(res);
            Intent i = new Intent(getActivity(), TrailerActivity.class);
            i.putExtra("trailerList", res);
            startActivity(i);
            Log.i("onPostExecute", "Tailer!!!!");
        }
    }
class Reviewfetch extends AsyncTask<Void, Void, ArrayList<Review>> {
    @Override
    protected ArrayList<Review> doInBackground(Void... params) {
        HttpURLConnection URLCon = null;
        BufferedReader buffered = null;
        String RevJson = null;
        final String BaseURl = ("https://api.themoviedb.org/3/movie/");
        Uri BuildUri = Uri.parse(BaseURl).buildUpon()
                .appendPath("206647")
                .appendPath("reviews")
                .appendQueryParameter("api_key", "944d2e37b62a28829abd7c15058021fb")
                .build();

        try {
            URL url = new URL(BuildUri.toString());
            URLCon = (HttpURLConnection) url.openConnection();
            URLCon.setRequestMethod("GET");
            URLCon.connect();
            InputStream inputStream = URLCon.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            buffered = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = buffered.readLine()) != null) {
                buffer.append(line + "/n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            RevJson = buffer.toString();
            Log.i("**************", RevJson);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (URLCon != null) {
                URLCon.disconnect();
            }
            if (buffered != null) {
                try {
                    buffered.close();
                } catch (final IOException e) {
                }
            }
        }
        try {
            return GetViewfromJSon(RevJson);
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Review> GetViewfromJSon(String ViewJSONSTR) throws JSONException {
        final String list = "results";
        String ID = "id";
        JSONObject movieJson = null;
        String[] resultstrs = null;
        ArrayList<Review> movierev = new ArrayList<>();
        try {
            movieJson = new JSONObject(ViewJSONSTR);
            JSONArray moviearray = movieJson.getJSONArray(list);
            for (int i = 0; i < moviearray.length(); i++) {
                JSONObject movie_data = moviearray.getJSONObject(i);
                String auther = movie_data.getString("author");
                String content = movie_data.getString("content");
                Log.i("rrrrr", auther);
                movierev.add(new Review(auther, content));
            }
        } catch (JSONException e) {
            Log.i("moviere", resultstrs.toString());
            {
            }
        }
        return movierev;
    }
    @Override
    protected void onPostExecute(ArrayList<Review> rev) {
        super.onPostExecute(rev);
        Log.i("orevex", rev.toString());
        Intent i = new Intent(getActivity(), ReviewActivity.class);
        i.putExtra("revList", rev);
        startActivity(i);
        Log.i("onPostExecute", "Startactivity!!!!");
    }
}
    public void fetchReview(){
        new Reviewfetch().execute();
    Log.i("Reviewfetch", "Reviewfetch!!!!");
    }
    public void fetchTrailer(){
        TailerFetch rv = new TailerFetch();
        rv.execute();
        Log.i("fetchTrailer", "fetchTrailer!!!!");
    }
}