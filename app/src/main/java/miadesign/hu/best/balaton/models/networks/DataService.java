package miadesign.hu.best.balaton.models.networks;

import android.app.IntentService;
import android.content.Intent;

import javax.inject.Inject;

import miadesign.hu.best.balaton.R;
import miadesign.hu.best.balaton.di.App;
import miadesign.hu.best.balaton.entities.Attraction;
import miadesign.hu.best.balaton.models.databases.AppDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataService extends IntentService {

    @Inject
    DataApi api;
    @Inject
    AppDatabase database;

    public DataService() {
        super("DataService");
        App.graph.inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        boolean forceUpdate = intent.getBooleanExtra("force", false);

        api.get().enqueue(new Callback<Attraction[]>() {

            @Override
            public void onResponse(Call<Attraction[]> call, Response<Attraction[]> response) {
                Attraction[] list = response.body();
                boolean conditions
                        = forceUpdate ? list != null && list.length > 0 :
                        list != null && list.length > 0 && database.buildingDao().countBuildings() == 0;
                if (conditions) {
                    database.buildingDao().insertAll(list);
                }
            }

            @Override
            public void onFailure(Call<Attraction[]> call, Throwable t) {
                Intent intent = new Intent("miadesign.hu.best.balaton");
                intent.putExtra(App.INTENT_ERROR, getString(R.string.network_error));
                sendBroadcast(intent);
            }

        });

    }

}
