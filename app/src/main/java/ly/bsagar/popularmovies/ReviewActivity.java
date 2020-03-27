package ly.bsagar.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ly.bsagar.popularmovies.Adapters.ReviewAdapter;
import ly.bsagar.popularmovies.POJO.JsonUtili;
import ly.bsagar.popularmovies.POJO.ReviewClass;
import ly.bsagar.popularmovies.POJO.TrailerClass;
import ly.bsagar.popularmovies.databinding.ActivityReviewBinding;

public class ReviewActivity extends AppCompatActivity {

    ActivityReviewBinding binding;
    String MOVIE_DETAIL_URL = "https://api.themoviedb.org/3/movie";
    String MOVIE_REVIEWS_URL = "https://api.themoviedb.org/3/movie/19404/reviews";
    private static final String API_KEY = BuildConfig.API_KEY;
    ArrayList<ReviewClass> trailers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar2);
        Intent intent = getIntent();
        String movieID = intent.getStringExtra("ID");
        String URL = Uri.parse(MOVIE_DETAIL_URL).buildUpon()
                .appendPath(movieID).appendPath("reviews")
                .appendQueryParameter("api_key",API_KEY)
                .build().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                popularUI(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

    private void popularUI(JSONObject response) {
        ArrayList<ReviewClass> reviews = new ArrayList<>();
        JSONArray array = new JSONArray();
        try {
            array = response.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        // show no reviews if reviews is empty
        if (array.length() == 0 ){
            binding.noReviews.setVisibility(View.VISIBLE);
            return;
        }
        //otherwise hide this view and parse the responce
        binding.noReviews.setVisibility(View.GONE);

        try {
            reviews = JsonUtili.parseJsonReview(array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ReviewAdapter reviewAdapter = new ReviewAdapter();
        reviewAdapter.setContent(reviews);
        binding.reviewRecycle.setLayoutManager(new LinearLayoutManager(this));
        binding.reviewRecycle.setAdapter(reviewAdapter);

    }
}
