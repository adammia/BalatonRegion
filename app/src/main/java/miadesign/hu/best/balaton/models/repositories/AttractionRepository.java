package miadesign.hu.best.balaton.models.repositories;

import android.arch.lifecycle.LiveData;

import miadesign.hu.best.balaton.entities.Attraction;

public interface AttractionRepository {
    LiveData<Attraction[]> getAll();
}
