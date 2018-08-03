package miadesign.hu.best.balaton.models.repositories;

import android.arch.lifecycle.LiveData;

import javax.inject.Inject;

import miadesign.hu.best.balaton.di.App;
import miadesign.hu.best.balaton.entities.Attraction;
import miadesign.hu.best.balaton.models.databases.AppDatabase;

public class AttractionRepositoryImpl implements AttractionRepository {

    @Inject
    AppDatabase database;

    public AttractionRepositoryImpl() {
        App.graph.inject(this);
    }

    @Override
    public LiveData<Attraction[]> getAll() {
        return database.buildingDao().getAll();
    }

}
