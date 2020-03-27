package ly.bsagar.popularmovies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ly.bsagar.popularmovies.POJO.MovieClass;
import ly.bsagar.popularmovies.POJO.ReviewClass;
import ly.bsagar.popularmovies.POJO.TrailerClass;
import ly.bsagar.popularmovies.R;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private ArrayList<ReviewClass> reviewArrayList;

    @NonNull
    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.review_item_layout, parent, false);
        return new ReviewAdapter.ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterViewHolder holder, int position) {
        holder.titleTV.setText(reviewArrayList.get(position).getTitle());
        holder.bodyTV.setText(reviewArrayList.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return (reviewArrayList == null) ? 0 : reviewArrayList.size();
    }

    public void setContent(ArrayList<ReviewClass> reviews) {
        reviewArrayList = reviews;
        notifyDataSetChanged();
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder  {
        TextView titleTV;
        TextView bodyTV;

        public ReviewAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.review_title);
            bodyTV = itemView.findViewById(R.id.review_body);
        }

    }

}
