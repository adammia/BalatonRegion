package miadesign.hu.best.balaton.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import miadesign.hu.best.balaton.di.App;
import miadesign.hu.best.balaton.entities.Attraction;
import miadesign.hu.best.balaton.models.repositories.AttractionRepository;

public class AttractionListViewModel extends ViewModel {

    private LiveData<Attraction[]> list;
    private LatLng position;
    @Inject
    AttractionRepository repo;

    public AttractionListViewModel() {

        App.graph.inject(this);
        this.list = repo.getAll();

    }

    public LiveData<Attraction[]> getList() {

        return this.list;

    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

}
