package miadesign.hu.best.balaton.di;

import javax.inject.Singleton;

import dagger.Component;
import miadesign.hu.best.balaton.models.networks.DataService;
import miadesign.hu.best.balaton.models.repositories.AttractionRepositoryImpl;
import miadesign.hu.best.balaton.models.repositories.FavoritesRepoImpl;
import miadesign.hu.best.balaton.viewmodels.AttractionListViewModel;
import miadesign.hu.best.balaton.viewmodels.DetailViewModel;
import miadesign.hu.best.balaton.views.widgets.WidgetProvider;

@Singleton
@Component(modules = {
        ContextModul.class,
        RetrofitModul.class,
        DatabaseModul.class,
        RepositoryModul.class,
        FavoritesModul.class
})

public interface Graph {
    void inject(DataService dataService);

    void inject(AttractionListViewModel attractionListViewModel);

    void inject(AttractionRepositoryImpl buildingsRepository);

    void inject(DetailViewModel detailViewModel);

    void inject(FavoritesRepoImpl favoritesRepo);

    void inject(WidgetProvider widgetProvider);
}
