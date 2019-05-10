package eu.bidtrans.popularmovies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MovieListFragment extends Fragment {
    RecyclerView mMovieListRecycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.movie_list_fragment, container, false);

        mMovieListRecycler = (RecyclerView) fragment.findViewById(R.id.movie_list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);

        mMovieListRecycler.setLayoutManager(layoutManager);

        return fragment;
    }
}
