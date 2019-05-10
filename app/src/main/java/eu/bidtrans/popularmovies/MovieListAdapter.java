package eu.bidtrans.popularmovies;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder> {
    private Uri[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imageView;
        public MyViewHolder(ImageView v) {
            super(v);
            imageView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MovieListAdapter(Uri[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MovieListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.imageView.setImageURI(mDataset[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
