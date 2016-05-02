package edu.otc.rileyre.popularmoviesapp;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * A placeholder fragment containing a simple view.
 * this class handles our movie grid. It inflates our fragment_main layout
 * uses an ImageAdapter to load up the images for each movie
 * creates a gridview for our gridview_movies layout component
 * and sets up our listener to see if an image in our grid is clicked
 */
public class MovieFragment extends Fragment {

    ImageAdapter mMovieAdapter;

    public MovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View fragment = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) fragment.findViewById(R.id.gridView_movies);

        mMovieAdapter = new ImageAdapter(getActivity());
        gridView.setAdapter(mMovieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                OurMovie ourMovie = mMovieAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), OurMovieDetailsActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, ourMovie);
                startActivity(intent);

            }
        });

        FetchMoviesUrls fetch = new FetchMoviesUrls();

        //final String api_key = getString(R.string.API_KEY);
        final String api_key = "f9f917adf24cc4bbd953ba9b2bc1b3d3";

        fetch.execute(api_key);

        return fragment;
    }

    public class FetchMoviesUrls extends AsyncTask<String, Void, List<OurMovie>> {
        private final String LOG_TAG = FetchMoviesUrls.class.getSimpleName();


        private List<OurMovie> getUrlsFromJson(String urlsJsonStr)
                throws JSONException {

            // Here are the JSON objects we need to extract

            final String RESULTS = "results";
            final String POSTER_URL = "poster_path";
            final String IMAGE_URL_BASE = "http://image.tmdb.org/t/p/w185/";


            List<OurMovie> urls = new ArrayList<>();
            JSONObject urlsJson = new JSONObject(urlsJsonStr);
            JSONArray resultsArray = urlsJson.getJSONArray(RESULTS);

            for (int i = 0; i < resultsArray.length(); i++) {
                final JSONObject result = resultsArray.getJSONObject(i);
                OurMovie ourMovie = new OurMovie();

                ourMovie.posterUrl = IMAGE_URL_BASE + result.getString((POSTER_URL));
                ourMovie.backdropUrl = IMAGE_URL_BASE + result.getString("backdrop_path");
                ourMovie.originalTitle = result.getString(("original_title"));
                ourMovie.overview = result.getString("overview");
                ourMovie.releaseDate = result.getString("release_date");
                ourMovie.voteAverage = result.getString("vote_average");

                urls.add(ourMovie);
            }

            return urls;
        }

        @Override
        protected List<OurMovie> doInBackground(String... params) {
            if (params.length == 0) {
                return null;                    // no parameter - nothing to look up
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;       // set up outside of try block so that finally can close

            String urlsJsonStr = null;          // will contain the Json response as a string

            try {
                final String MOVIEDB_BASE_URL =
                        "http://api.themoviedb.org/3/discover/movie?";
                final String SORTBY_PARAM =
                        "sort_by";
                final String API_KEY_PARAM =
                        "api_key";

                Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                        .appendQueryParameter(SORTBY_PARAM, "popularity.desc")
                        .appendQueryParameter(API_KEY_PARAM, params[0])
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append((line + "\n"));

                }

                if (buffer.length() == 0) {
                    return null;                    // no data, return  null value
                }

                urlsJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;                        // no data, return null value
            } finally {                             // close all connections
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error Closing stream", e);
                    }
                }
            }

            try {
                return getUrlsFromJson(urlsJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;                        // no data if error
        }

        @Override
        protected void onPostExecute(List<OurMovie> result) {
            if (result != null) {
                mMovieAdapter.updateURLS(result);
            }
        }


    }
}

