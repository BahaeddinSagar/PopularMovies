package ly.bsagar.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import ly.bsagar.popularmovies.POJO.JsonUtili;
import ly.bsagar.popularmovies.POJO.MovieClass;
import ly.bsagar.popularmovies.Adapters.MoviesAdapter;
import ly.bsagar.popularmovies.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieAdapterClickHandler {
    ActivityMainBinding binding;
    private static final String TAG = "MainActivity";
    private final String POPULAR_URL="https://api.themoviedb.org/3/movie/popular";
    private final String TOPRATED_URL="https://api.themoviedb.org/3/movie/top_rated";
    private static final String API_KEY = BuildConfig.API_KEY;
    ArrayList<MovieClass> movies = new ArrayList<>();
    int page = 1;
    MoviesAdapter moviesAdapter;
    boolean popular = true;
    RequestQueue requestQueue ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);
        requestQueue = Volley.newRequestQueue(this);
        fetchData(popular,page);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData(popular, Integer.parseInt(binding.editText.getText().toString()));
            }
        });

    }

    private void fetchData(boolean b, int page) {

        Uri.Builder uriBuilder ;
        if (b) {
            uriBuilder = Uri.parse(POPULAR_URL).buildUpon();
        } else {
            uriBuilder = Uri.parse(TOPRATED_URL).buildUpon();
        }
        uriBuilder.appendQueryParameter("api_key", API_KEY);
        uriBuilder.appendQueryParameter("page", String.valueOf(page));
        String URL = uriBuilder.build().toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                populateUI(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, R.string.net_err, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void populateUI(JSONObject response) {

        try {
            movies = JsonUtili.parseJsonMovie(response.getJSONArray("results"));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.err_fetch, Toast.LENGTH_SHORT).show();
            return;
        }

        moviesAdapter =new MoviesAdapter(this);
        moviesAdapter.setContent(movies);
        binding.recylerView.setLayoutManager(new GridLayoutManager(this,2));
        binding.recylerView.setAdapter(moviesAdapter);


    }
    // recycle view on click listener
    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("movie", Parcels.wrap(movies.get(position)));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: " + item.getItemId());
        switch (item.getItemId()){
            case R.id.pop :
                popular = true;
                movies.clear();
                moviesAdapter.notifyDataSetChanged();
                fetchData(popular,page);
                return true;
            case R.id.rate:
                popular = false;
                movies.clear();
                moviesAdapter.notifyDataSetChanged();
                fetchData(popular,page);
                return true;
            default:
                return false;
        }
    }
}
