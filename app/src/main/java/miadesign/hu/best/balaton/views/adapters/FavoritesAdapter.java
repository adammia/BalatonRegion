package miadesign.hu.best.balaton.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import miadesign.hu.best.balaton.databinding.ListRowBinding;
import miadesign.hu.best.balaton.di.App;
import miadesign.hu.best.balaton.entities.Attraction;
import miadesign.hu.best.balaton.views.AttractionActivity;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<Attraction> list;
    private Context context;

    public FavoritesAdapter(Context context, List<Attraction> list) {
        this.context = context;
        List<Attraction> filteredList = new ArrayList<>();
        for (Attraction element : list) {
            if (element.isFavorite()) {
                filteredList.add(element);
            }
        }
        this.list = filteredList;
    }

    @Override
    @NonNull
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListRowBinding bind = ListRowBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(bind);

    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder viewHolder, int position) {

        viewHolder.bind(list.get(position));

    }

    @Override
    public int getItemCount() {

        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ListRowBinding bind;

        private ViewHolder(ListRowBinding binding) {

            super(binding.getRoot());
            this.bind = binding;

        }

        @SuppressWarnings("unchecked")
        public void bind(Attraction attraction) {

            bind.setAttraction(attraction);
            Picasso.get().load(attraction.getMainImage()).into(bind.attractionImage);
            bind.attractionImage.setOnClickListener(view -> {
                Intent intent = new Intent(context, AttractionActivity.class);
                intent.putExtra(App.INTENT_BUILDING, attraction);
                Pair<View, String> image = Pair.create(bind.attractionImage, "main_image");
                Pair<View, String> title = Pair.create(bind.attractionName, "title_name");
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation((Activity) context, image, title);
                context.startActivity(intent, options.toBundle());
            });
            bind.executePendingBindings();

        }

    }

}

