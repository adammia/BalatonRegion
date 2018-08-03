package miadesign.hu.best.balaton.models.repositories;

import android.arch.lifecycle.LiveData;

import miadesign.hu.best.balaton.entities.Attraction;

public interface FavoritesRepo {

    LiveData<Attraction> get(String id);

    void deleteFavorite(String id);

    void insertFavorite(String id);

}
