package miadesign.hu.best.balaton.views;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.Objects;

import miadesign.hu.best.balaton.R;
import miadesign.hu.best.balaton.databinding.FragmentListBinding;
import miadesign.hu.best.balaton.viewmodels.AttractionListViewModel;
import miadesign.hu.best.balaton.views.adapters.AttractionAdapter;

public class AttractionListFragment extends Fragment {

    private FragmentListBinding binding;

    public AttractionListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        binding.list.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        binding.list.setLayoutManager(lm);

        this.observe();

        return binding.getRoot();

    }

    private void observe() {
        FragmentActivity activity = Objects.requireNonNull(getActivity());
        AttractionListViewModel viewModel = ViewModelProviders.of(activity).get(AttractionListViewModel.class);
        viewModel.getList().observe(this, list -> {
            if (list != null) {
                AttractionAdapter adapter = new AttractionAdapter(getActivity(), Arrays.asList(list));
                binding.list.setAdapter(adapter);
            }
        });

    }

}
