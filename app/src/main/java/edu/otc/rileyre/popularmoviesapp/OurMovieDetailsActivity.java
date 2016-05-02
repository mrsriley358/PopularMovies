package edu.otc.rileyre.popularmoviesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by rileyre on 4/30/2016.
 */
public class OurMovieDetailsActivity extends Activity implements OurMovieHandler {

    private OurMovie ourMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ourMovie = getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        setContentView(R.layout.list_item_movies);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public OurMovie getOurMovie() { return ourMovie; }
}
