package miadesign.hu.best.balaton.di;

import android.app.Application;

public class App extends Application {

    public static Graph graph;
    public static final String INTENT_BUILDING = "parcelable building intent tag";
    public static final String INTENT_ERROR = "service error, check network";

    @Override
    public void onCreate() {

        super.onCreate();
        graph = DaggerGraph.builder()
                .contextModul(new ContextModul(this))
                .retrofitModul(new RetrofitModul())
                .databaseModul(new DatabaseModul(this))
                .repositoryModul(new RepositoryModul())
                .favoritesModul(new FavoritesModul())
                .build();

    }

}
