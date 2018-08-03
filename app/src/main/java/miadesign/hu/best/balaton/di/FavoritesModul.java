package miadesign.hu.best.balaton.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import miadesign.hu.best.balaton.models.repositories.FavoritesRepo;
import miadesign.hu.best.balaton.models.repositories.FavoritesRepoImpl;

@Module
public class FavoritesModul {

    @Provides
    @Singleton
    public FavoritesRepo provides() {
        return new FavoritesRepoImpl();
    }

}
