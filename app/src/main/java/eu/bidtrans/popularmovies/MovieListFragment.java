package eu.bidtrans.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import eu.bidtrans.popularmovies.domain.Movie;
import eu.bidtrans.popularmovies.dto.MovieDTO;
import eu.bidtrans.popularmovies.dto.MovieMapper;

public class MovieListFragment extends Fragment {
    private static final String TAG = Fragment.class.getSimpleName();
    private static final String SORT_POPULARITY_DESC = "popularity.desc";

    RecyclerView mMovieListRecycler;
    MovieListAdapter mMovieListAdapter;
//    List<MoviePoster> moviePosters;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_movie_list_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_settings_id) {
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.movie_list_fragment, container, false);

        mMovieListRecycler = (RecyclerView) fragment.findViewById(R.id.movie_list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mMovieListRecycler.setLayoutManager(layoutManager);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshMovies();
    }

    private String getSortOrderFromPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String sortOrder = SORT_POPULARITY_DESC;
        if (prefs != null) {
            sortOrder = prefs.getString(getString(R.string.sort_order_key), SORT_POPULARITY_DESC);
        }
        return sortOrder;
    }

    private void refreshMovies() {
        FetchMoviePosterData updateTask = new FetchMoviePosterData();
        updateTask.execute(getSortOrderFromPreferences());
    }

    private void setAdapterData(List<MovieDTO> posters) {
        List<Movie> movies = new ArrayList<>();
        for (MovieDTO dto : posters
        ) {
            movies.add(MovieMapper.toMovieDetail(dto));
        }
        mMovieListAdapter = new MovieListAdapter(movies.toArray(new Movie[movies.size()]), getActivity().getApplicationContext());
        mMovieListRecycler.setAdapter(mMovieListAdapter);
    }

    private class FetchMoviePosterData extends AsyncTask<String, Void, List<MovieDTO>> {
        private static final String PARAM_SORT_BY = "sort_by";
        private static final String PARAM_PAGE = "page";
        private static final String PARAM_API_KEY = "api_key";

        private Uri getUri(String sortOrder) {
            Uri.Builder builder = Uri.parse("https://api.themoviedb.org/3/discover/movie?").buildUpon();
            builder.appendQueryParameter(PARAM_API_KEY, BuildConfig.MOVIE_DB_API_KEY)
                    .appendQueryParameter(PARAM_SORT_BY, sortOrder)
                    .appendQueryParameter(PARAM_PAGE, "1");
            Uri uri = builder.build();
            return uri;
        }

        private String getMoviesAsJsonString(String urlString) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String jsonStr = null;

            try {
                URL url = new URL(urlString);

                // Create the request to MovieDB, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }
            return jsonStr;
        }

        private List<MovieDTO> getMovieList(String sortOrder) {
            String url = getUri(sortOrder).toString();
            String jsonData = getMoviesAsJsonString(url);
            List<MovieDTO> result = new ArrayList<>();
            if (jsonData != null) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    JsonNode rootNode = mapper.readTree(jsonData);
                    JsonNode resultsNode = rootNode.get("results");
                    String resultsString = resultsNode.toString();
                    result = mapper.readValue(resultsString, new TypeReference<List<MovieDTO>>() {
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        protected List<MovieDTO> doInBackground(String... params) {
            String sortOrder = params[0];
            List<MovieDTO> dtoMovieList = getMovieList(sortOrder);
            return dtoMovieList;
        }

        @Override
        protected void onPostExecute(List<MovieDTO> data) {
            setAdapterData(data);
        }
    }
}
