package ly.bsagar.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.text.MessageFormat;
import java.util.ArrayList;

import ly.bsagar.popularmovies.Adapters.TrailersAdapter;
import ly.bsagar.popularmovies.POJO.JsonUtili;
import ly.bsagar.popularmovies.POJO.MovieClass;
import ly.bsagar.popularmovies.POJO.TrailerClass;
import ly.bsagar.popularmovies.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity implements TrailersAdapter.TrailerAdapterClickHandler {
    private static final String TAG = "TrailersList";
    ActivityDetailBinding binding;
    String MOVIE_DETAIL_URL = "https://api.themoviedb.org/3/movie";
    String YOUTUBE = "https://www.youtube.com/watch";
    String MOVIE_REVIEWS_URL = "https://api.themoviedb.org/3/movie/19404/reviews";
    private static final String API_KEY = BuildConfig.API_KEY;
    ArrayList<TrailerClass> trailers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTheme(R.style.AppThemeNoActionBar);

        final Intent intent = getIntent();
        final MovieClass movie = Parcels.unwrap(intent.getParcelableExtra("movie"));

        binding.overview.append(movie.getOverview());
        binding.voteAvr.setText(MessageFormat.format("{0}/10", movie.getVote_average()));
        binding.releaseYear.append(movie.getReleaseDate());

        Picasso.get().load(movie.getBackDrop())
                .into(binding.expandedImage);

        showTrailersList(movie.getMovieID());

        //still don't fully understand this part for collabsable toolbar, will have to revisit in the future
        setSupportActionBar(binding.toolbar);
        setTitle(movie.getTitle());
        binding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                } else if (isShow) {
                    isShow = false;
                }
            }
        });

        // click to go to review page
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), ReviewActivity.class);
                intent2.putExtra("ID", movie.getMovieID());
                startActivity(intent2);
            }
        });
    }

    private void showTrailersList(String movieID) {
        Uri.Builder builder = Uri.parse(MOVIE_DETAIL_URL).buildUpon().appendPath(movieID).appendPath("videos").appendQueryParameter("api_key", API_KEY);
        String newURL = builder.build().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, newURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                populateTrailers(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DetailActivity.this, "Error Making request", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    private void populateTrailers(JSONObject response) {
        try {
            trailers = JsonUtili.parseJsonTrailer(response.getJSONArray("results"));
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        TrailersAdapter trailersAdapter = new TrailersAdapter(this);
        trailersAdapter.setContent(trailers);

        binding.trailers.setLayoutManager(new LinearLayoutManager(this));
        binding.trailers.setAdapter(trailersAdapter);


    }
    // recycle view on click listener
    @Override
    public void onClick(int position) {
        Uri.Builder builder = Uri.parse(YOUTUBE).buildUpon().appendQueryParameter("v", trailers.get(position).getKey());
        Uri uri = builder.build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }
}
