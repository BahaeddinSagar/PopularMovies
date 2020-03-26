package ly.bsagar.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
        String title =intent.getStringExtra("title");
        String overview = intent.getStringExtra("overview");
        String rating = intent.getStringExtra("rating");
        String release = intent.getStringExtra("release");
        String image = intent.getStringExtra("image");


        binding.overview.append(overview);
        binding.voteAvr.setText(rating+"/10");
        binding.releaseYear.append(release);

        Picasso.get().load(image)
                .into(binding.expandedImage);

        //still don't fully understand this part for collabsable toolbar, will have to revisit in the future
        setSupportActionBar(binding.toolbar);
        setTitle(title);
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
