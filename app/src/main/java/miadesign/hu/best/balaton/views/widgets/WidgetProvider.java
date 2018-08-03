package miadesign.hu.best.balaton.views.widgets;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;

import java.util.concurrent.ThreadLocalRandom;

import javax.inject.Inject;

import miadesign.hu.best.balaton.R;
import miadesign.hu.best.balaton.di.App;
import miadesign.hu.best.balaton.entities.Attraction;
import miadesign.hu.best.balaton.models.databases.AppDatabase;
import miadesign.hu.best.balaton.views.AttractionActivity;

public class WidgetProvider extends AppWidgetProvider {

    @Inject
    AppDatabase database;
    private static Attraction[] list;
    private static int chooseRandomIndex;

    public WidgetProvider() {
        App.graph.inject(this);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // create new thread for querying database
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                list = database.buildingDao().getAllForWidget();
                return list.length;
            }

            @Override
            protected void onPostExecute(Integer integer) {

                for (int appWidgetId : appWidgetIds) {
                    chooseRandomIndex = ThreadLocalRandom.current().nextInt(0, integer);
                    Attraction attraction = list[chooseRandomIndex];
                    Intent intent = new Intent(context, AttractionActivity.class);
                    intent.putExtra(App.INTENT_BUILDING, attraction);
                    PendingIntent pendingIntent = TaskStackBuilder
                            .create(context)
                            .addNextIntentWithParentStack(intent)
                            .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
                    views.setOnClickPendingIntent(R.id.layout, pendingIntent);
                    views.setTextViewText(R.id.title, attraction.getName());
                    views.setTextViewText(R.id.description, attraction.getDescription());
                    Picasso.get()
                            .load(attraction.getMainImage())
                            .into(views, R.id.image, new int[]{appWidgetId});
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                }

            }
        }.execute();

    }

}
