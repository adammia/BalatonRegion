package miadesign.hu.best.balaton.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import miadesign.hu.best.balaton.models.repositories.AttractionRepository;
import miadesign.hu.best.balaton.models.repositories.AttractionRepositoryImpl;

@Module
public class RepositoryModul {

    @Provides
    @Singleton
    AttractionRepository provides(){
        return new AttractionRepositoryImpl();
    }

}
