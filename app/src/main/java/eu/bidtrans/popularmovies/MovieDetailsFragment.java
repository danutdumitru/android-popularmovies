package eu.bidtrans.popularmovies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.Calendar;

import eu.bidtrans.popularmovies.domain.Movie;

public class MovieDetailsFragment extends Fragment {

    private static final String TAG = MovieDetailsFragment.class.getSimpleName();
    public static final String MOVIE_DETAIL = "movie_detail";

    private Movie movie;
    private TextView textTitle;
    private ImageView imagePoster;
    private TextView textYear, textLength, textRating, textOverview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.movie_details_fragment, container, false);

        textTitle = (TextView) fragment.findViewById(R.id.text_title);
        imagePoster = (ImageView) fragment.findViewById(R.id.image_view_poster);
        textYear = (TextView) fragment.findViewById(R.id.text_year);
        textLength = (TextView) fragment.findViewById(R.id.text_length);
        textRating = (TextView) fragment.findViewById(R.id.text_rating);
        textOverview = (TextView) fragment.findViewById(R.id.text_overview);

        movie = (Movie) getActivity().getIntent().getSerializableExtra(MOVIE_DETAIL);
        setMovieData();
        return fragment;
    }

    private void setMovieData() {
        textTitle.setText(movie.getOriginalTitle());
        Picasso.get().load(MovieUri.getImageUri(movie)).into(imagePoster);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(movie.getReleaseDate());
        textYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        textLength.setText("120min");
        textRating.setText(String.format("%.1f/10",movie.getVoteAverage()));
        textOverview.setText(movie.getOverview());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

