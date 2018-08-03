package miadesign.hu.best.balaton.views;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.Objects;

import miadesign.hu.best.balaton.R;
import miadesign.hu.best.balaton.databinding.FragmentFavoriteBinding;
import miadesign.hu.best.balaton.viewmodels.AttractionListViewModel;
import miadesign.hu.best.balaton.views.adapters.FavoritesAdapter;
import miadesign.hu.best.balaton.views.custom.ItemDecoration;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    private FragmentActivity activity;

    public FavoriteFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = Objects.requireNonNull(getActivity());

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false);
        binding.list.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.list.addItemDecoration(new ItemDecoration(activity, R.dimen.margin_16, 2));
        binding.list.setLayoutManager(lm);

        this.observe();

        return binding.getRoot();

    }

    private void observe() {
        AttractionListViewModel viewModel = ViewModelProviders.of(activity).get(AttractionListViewModel.class);
        viewModel.getList().observe(this, list -> {
            if (list != null) {
                FavoritesAdapter adapter = new FavoritesAdapter(activity, Arrays.asList(list));
                binding.list.setAdapter(adapter);
            }
        });

    }

}
