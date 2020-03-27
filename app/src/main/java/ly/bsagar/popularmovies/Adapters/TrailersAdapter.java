package ly.bsagar.popularmovies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ly.bsagar.popularmovies.POJO.TrailerClass;
import ly.bsagar.popularmovies.R;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerAdapterViewHolder> {
    private ArrayList<TrailerClass> trailersArrayList;
    private TrailerAdapterClickHandler trailerAdapterClickHandler;
    // interface for click handling
    public interface TrailerAdapterClickHandler {
        void onClick(int position);
    }

    public TrailersAdapter( TrailerAdapterClickHandler trailerAdapterClickHandler){
        this.trailerAdapterClickHandler = trailerAdapterClickHandler;
    }


    @NonNull
    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.trailer_item_layout, parent, false);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapterViewHolder holder, int position) {
        holder.titleTV.setText(trailersArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return (trailersArrayList == null) ? 0 : trailersArrayList.size();
    }

    public void setContent(ArrayList<TrailerClass> trailers) {
        trailersArrayList = trailers;
        notifyDataSetChanged();
    }



    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTV;

        public TrailerAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.trailer_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            trailerAdapterClickHandler.onClick(this.getLayoutPosition());

        }
    }


}
