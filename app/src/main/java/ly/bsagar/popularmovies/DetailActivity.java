package ly.bsagar.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.text.MessageFormat;

import ly.bsagar.popularmovies.Utils.MovieClass;
import ly.bsagar.popularmovies.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTheme(R.style.AppThemeNoActionBar);

        Intent intent = getIntent();
        MovieClass movie = Parcels.unwrap(intent.getParcelableExtra("movie"));

        binding.overview.append(movie.getOverview());
        binding.voteAvr.setText(MessageFormat.format("{0}/10", movie.getVote_average()));
        binding.releaseYear.append(movie.getReleaseDate());

        Picasso.get().load(movie.getBackDrop())
                .into(binding.expandedImage);

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



    }
}
