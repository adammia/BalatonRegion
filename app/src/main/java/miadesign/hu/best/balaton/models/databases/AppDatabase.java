package miadesign.hu.best.balaton.models.databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import miadesign.hu.best.balaton.di.DatabaseModul;
import miadesign.hu.best.balaton.entities.Attraction;

@Database(entities = {Attraction.class}, version = DatabaseModul.VERSION)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AttractionDAO buildingDao();

}
