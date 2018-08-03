package miadesign.hu.best.balaton.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModul {

    private Context context;

    public ContextModul(Application application) {
        this.context = application.getBaseContext();
    }

    @Singleton
    @Provides
    public Context provide() {
        return context;
    }

}
