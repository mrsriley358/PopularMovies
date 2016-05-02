package edu.otc.rileyre.popularmoviesapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by rileyre on 4/30/2016.
 */
public class OurMovieDetailsActivityFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public OurMovie ourMovie;

    public OurMovieDetailsActivityFragment() {
        // required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.list_item_movies, container, false);

        TextView yearTextView = (TextView) view.findViewById(R.id.list_item_movies_year);
        yearTextView.setText(ourMovie.releaseDate.split("-")[0]);

        TextView overviewTextView = (TextView) view.findViewById(R.id.list_item_movies_overview);
        overviewTextView.setText(ourMovie.overview);

        TextView ratingTextView = (TextView) view.findViewById(R.id.list_item_movies_userrating);
        overviewTextView.setText(ourMovie.voteAverage);

        TextView titleTextView = (TextView) view.findViewById(R.id.list_item_movies_titles_textview);
        overviewTextView.setText(ourMovie.originalTitle);

        ImageView imageView = (ImageView) view.findViewById(R.id.list_item_movies_titles_imageview);
        final Picasso picasso = Picasso.with(getActivity());
        picasso.load(ourMovie.posterUrl).into(imageView);

        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity){
            a=(Activity) context;
            if (a instanceof OurMovieHandler) {
                ourMovie = ((OurMovieHandler) a).getOurMovie();
            }
        }

    }
}
