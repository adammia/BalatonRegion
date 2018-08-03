package miadesign.hu.best.balaton.viewmodels;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import miadesign.hu.best.balaton.di.App;
import miadesign.hu.best.balaton.models.repositories.FavoritesRepo;

public class DetailViewModel extends ViewModel {

    @Inject
    FavoritesRepo repo;

    public DetailViewModel() {
        App.graph.inject(this);
    }

    public void setFavorite(String id) {
        repo.insertFavorite(id);
    }

    public void deleteFavorite(String id) {
        repo.deleteFavorite(id);
    }

}
