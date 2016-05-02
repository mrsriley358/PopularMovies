package edu.otc.rileyre.popularmoviesapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rileyre on 4/30/2016.
 *
 * from Android tutorial and broca
 */
public class OurMovie implements Parcelable {
    public String posterUrl;
    public String originalTitle;
    public String backdropUrl;
    public String overview;
    public String voteAverage;
    public String releaseDate;

    public OurMovie() {}

    public OurMovie(Parcel in) {
        posterUrl = in.readString();
        originalTitle = in.readString();
        backdropUrl = in.readString();
        overview = in.readString();
        voteAverage = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<OurMovie> CREATOR = new Creator<OurMovie>() {
        @Override
        public OurMovie createFromParcel(Parcel source) {
            return new OurMovie(source);
        }

        @Override
        public OurMovie[] newArray(int size) {
            return new OurMovie[size];
        }
    };

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterUrl);
        dest.writeString(originalTitle);
        dest.writeString(backdropUrl);
        dest.writeString(overview);
        dest.writeString(voteAverage);
        dest.writeString(releaseDate);
    }
}
