package com.example.future.popularmovie;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

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
import java.util.List;

public class FilmFragment extends Fragment {
    //private JSONObject moviearray;

    Custom_adapter adapter;
    //Context context;
    private GridView gridView;
    private String baseUrl;

    public FilmFragment() {
        Log.e("FilmFragment", "besmallah");
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.popular) {
            baseUrl = ("https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&");
            Uri builtUri = Uri.parse(baseUrl).buildUpon()
                    .appendQueryParameter("api_key", "944d2e37b62a28829abd7c15058021fb")
                    .build();
            updatefilming(builtUri.toString());
            return true;
        } else if (id == R.id.rate) {

            baseUrl = ("https://api.themoviedb.org/3/discover/movie?sort_by=top_rated&");
            Uri builtUri = Uri.parse(baseUrl).buildUpon()
                    .appendQueryParameter("api_key", "944d2e37b62a28829abd7c15058021fb")
                    .build();
            updatefilming(builtUri.toString());
            return true;
        } else {

            adapter = new Custom_adapter(getActivity(), viewFromDataBase());
            gridView.setAdapter(adapter);
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<aflamko> viewFromDataBase() {
        ArrayList<aflamko> list = new ArrayList<>();
        MovieDbHelper helper = new MovieDbHelper(getActivity());
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query(MovieDbHelper.TABLE_NAME, new String[]{MovieDbHelper.COLUMN_NAME_ID, MovieDbHelper.COLUMN_NAME_title, MovieDbHelper.COLUMN_NAME_overview, MovieDbHelper.COLUMN_NAME_poster, MovieDbHelper.COLUMN_NAME_release_data, MovieDbHelper.COLUMN_NAME_vote_average}, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                String flim_id = c.getString(c.getColumnIndex(MovieDbHelper.COLUMN_NAME_ID));
                String flim_name = c.getString(c.getColumnIndex(MovieDbHelper.COLUMN_NAME_title));
                String flim_vote = c.getString(c.getColumnIndex(MovieDbHelper.COLUMN_NAME_vote_average));
                String flim_release = c.getString(c.getColumnIndex(MovieDbHelper.COLUMN_NAME_release_data));
                String flim_poster = c.getString(c.getColumnIndex(MovieDbHelper.COLUMN_NAME_poster));
                String flim_over = c.getString(c.getColumnIndex(MovieDbHelper.COLUMN_NAME_overview));

                list.add(new aflamko(flim_id, flim_poster, flim_name, flim_over, flim_vote, flim_release));

            } while (c.moveToNext());
        }

        return list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        gridView = (GridView) rootView.findViewById(R.id.gridView_film);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                aflamko Dfilm = (aflamko) parent.getItemAtPosition(position);
                ((MainActivity) getActivity()).setAflam(Dfilm);

            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        baseUrl = ("https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&");
        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("api_key", "944d2e37b62a28829abd7c15058021fb")
                .build();
        updatefilming(builtUri.toString());
        Toast.makeText(getActivity(),"Start",Toast.LENGTH_SHORT).show();
    }

    public void updatefilming(String url) {
        new FetchFilmtask().execute(url);

    }

    public class FetchFilmtask extends AsyncTask<String, Void, ArrayList<aflamko>> {
        private final String LOG_TAG = FetchFilmtask.class.getSimpleName();


        @Override
        protected ArrayList<aflamko> doInBackground(String... params) {
            Log.e("DOinbackground", " filmFragment");
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieJsonStr = null;

            try {

                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    Log.e("inputstream", " filmFragment");
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                movieJsonStr = buffer.toString();

            } catch (IOException e) {

                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {

                    }
                }
            }
            try {
                return getmovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        public ArrayList<aflamko> getmovieDataFromJson(String movieJsonStr)
                throws JSONException {

            final String list = "results";
            final String poster_path = "poster_path";
            final String overview = "overview";
            final String release_date = "release_date";
            final String vote_average = "vote_average";
            final String original_title = "original_title";
            final String movie_id = "id";
            JSONObject movieJson = null;
            String title;
            String view;
            String vote;
            String release;
            String poster;
            String mid;

            ArrayList<aflamko> movieList = new ArrayList<>();

            try {
                movieJson = new JSONObject(movieJsonStr);
                JSONArray moviearray = movieJson.getJSONArray(list);
                for (int i = 0; i < moviearray.length(); i++) {
                    JSONObject movie_data = moviearray.getJSONObject(i);
                    mid = movie_data.getString(movie_id);
                    poster = movie_data.getString(poster_path);
                    view = movie_data.getString(overview);
                    release = movie_data.getString(release_date);
                    vote = movie_data.getString(vote_average);
                    title = movie_data.getString(original_title);
                    movieList.add(new aflamko(mid, poster, title, view, vote, release));
                }
            } catch (JSONException e) {
                Log.e("Test", "JSONException: " + e.getMessage());

            }
            return movieList;
        }

        @Override
        protected void onPostExecute(ArrayList<aflamko> result) {
            Toast.makeText(getActivity(), "be", Toast.LENGTH_SHORT).show();

            if (result != null) {
                Toast.makeText(getActivity(), "post", Toast.LENGTH_SHORT).show();

                adapter = new Custom_adapter(getActivity(), result);
                gridView.setAdapter(adapter);
            }
        }
    }

    public interface Callback {
        public void setAflam(aflamko aflam);
    }
}