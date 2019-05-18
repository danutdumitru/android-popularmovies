package eu.bidtrans.popularmovies;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.movie_details_activity, new MovieDetailsFragment())
                    .commit();
    }


}
