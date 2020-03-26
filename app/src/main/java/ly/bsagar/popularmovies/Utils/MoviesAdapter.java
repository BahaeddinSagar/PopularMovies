package ly.bsagar.popularmovies.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ly.bsagar.popularmovies.R;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {
    private ArrayList<MovieClass> moviesArrayList;
    private MovieAdapterClickHandler movieAdapterClickHandler;

    public interface MovieAdapterClickHandler {
        void onClick(int position);
    }

    public MoviesAdapter(MovieAdapterClickHandler movieAdapterClickHandler) {
        this.movieAdapterClickHandler = movieAdapterClickHandler;
    }

    @NonNull
    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_layout, parent, false);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapterViewHolder holder, int position) {
        holder.titleTV.setText(moviesArrayList.get(position).getTitle());
        holder.raitingTV.setText(moviesArrayList.get(position).getVote_average() + "/10");
        Picasso.get().load(moviesArrayList.get(position).getImagePath())
                .error(android.R.drawable.stat_notify_error)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return (moviesArrayList == null) ? 0 : moviesArrayList.size();
    }


    public void setContent(ArrayList<MovieClass> movies) {
        moviesArrayList = movies;
        notifyDataSetChanged();
    }

    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView titleTV;
        TextView raitingTV;

        public MoviesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTV = itemView.findViewById(R.id.title);
            raitingTV = itemView.findViewById(R.id.raiting);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            movieAdapterClickHandler.onClick(this.getLayoutPosition());

        }
    }


}
