package miadesign.hu.best.balaton.views;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import miadesign.hu.best.balaton.R;
import miadesign.hu.best.balaton.databinding.ActivityAttractionBinding;
import miadesign.hu.best.balaton.di.App;
import miadesign.hu.best.balaton.entities.Attraction;
import miadesign.hu.best.balaton.viewmodels.DetailViewModel;
import miadesign.hu.best.balaton.views.adapters.ImagesAdapter;

public class AttractionActivity extends AppCompatActivity {

    private ActivityAttractionBinding binding;
    private DetailViewModel viewModel;
    private Boolean attractionIsFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_attraction);
        Attraction attraction = getIntent().getParcelableExtra(App.INTENT_BUILDING);

        if (savedInstanceState == null) {
            attractionIsFavorite = attraction.isFavorite();
        } else {
            attractionIsFavorite = savedInstanceState.getBoolean("attractionIsFavorite");
        }
        binding.content.setAttraction(attraction);

        if (attraction == null) {
            binding.fab.setVisibility(View.GONE);
            Toast.makeText(this, R.string.attraction_error, Toast.LENGTH_LONG).show();
        } else {
            setUpToolbar(attraction);
            setUpFabButton(attraction);
            binding.content.imagesPreview.hasFixedSize();
            binding.content.imagesPreview.setAdapter(
                    new ImagesAdapter(this, attraction.getImages())
            );
            binding.content.imagesPreview.setLayoutManager(
                    new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            );
        }

    }

    private void setUpToolbar(Attraction attraction) {

        setSupportActionBar(binding.toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (attraction != null) {
            getSupportActionBar().setTitle(attraction.getName());
            Picasso.get().load(attraction.getMainImage()).placeholder(R.mipmap.placeholder).into(binding.mainImage);
        }

    }

    private void setUpFabButton(Attraction attraction) {

        int iconId = attraction.isFavorite() ? R.drawable.ic_favorite : R.drawable.ic_favorite_empty;
        binding.fab.setImageDrawable(getDrawable(iconId));
        binding.fab.setOnClickListener(view -> {
            if (attraction.isFavorite()) {
                viewModel.deleteFavorite(attraction.getName());
                binding.fab.setImageDrawable(getDrawable(R.drawable.ic_favorite_empty));
                attractionIsFavorite = false;
            } else {
                viewModel.setFavorite(attraction.getName());
                binding.fab.setImageDrawable(getDrawable(R.drawable.ic_favorite));
                attractionIsFavorite = true;
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("attractionIsFavorite", attractionIsFavorite);
        super.onSaveInstanceState(outState);
    }
}
