package miadesign.hu.best.balaton.models.databases;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import miadesign.hu.best.balaton.entities.Attraction;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface AttractionDAO {

    @Query("SELECT * FROM Attraction")
    LiveData<Attraction[]> getAll();

    @Query("SELECT * FROM Attraction")
    Attraction[] getAllForWidget();

    @Insert(onConflict = REPLACE)
    void insertAll(Attraction... attractions);

    // select single element by ID
    @Query("select * from Attraction where name = :id")
    LiveData<Attraction> getBuilding(String id);

    // count element in building table
    @Query("select count(*) from Attraction")
    int countBuildings();

    @Query("update Attraction set favorite = 1 where name = :id")
    void insertFavorite(String id);

    @Query("update Attraction set favorite = 0 where name = :id")
    void deleteFavorite(String id);

}
