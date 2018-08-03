package miadesign.hu.best.balaton.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import miadesign.hu.best.balaton.models.databases.AppDatabase;

@Module
public class DatabaseModul {

    private Context context;
    public static final int VERSION = 1;

    public DatabaseModul(Application app) {

        this.context = app.getApplicationContext();

    }

    @Provides
    @Singleton
    AppDatabase getDatabase() {

        return Room
                .databaseBuilder(context, AppDatabase.class, "app-data")
                .fallbackToDestructiveMigration()
                .build();

    }

}