package eu.bidtrans.popularmovies;

import android.net.Uri;

import eu.bidtrans.popularmovies.domain.Movie;

public class MovieUri {
    public static Uri getImageUri(Movie movie) {
        if (movie.getPosterPath() == null) {
            return null;
        }
        Uri uri = Uri.parse("http://image.tmdb.org/t/p/w500").buildUpon().appendPath(movie.getPosterPath().substring(1)).build();
        return uri;
    }
}
