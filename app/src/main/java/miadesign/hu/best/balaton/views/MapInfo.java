package miadesign.hu.best.balaton.views;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import miadesign.hu.best.balaton.R;

public class MapInfo implements GoogleMap.InfoWindowAdapter {

    private Activity activity;

    public MapInfo(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        View view = View.inflate(activity, R.layout.map_info, null);
        TextView title = view.findViewById(R.id.title);
        ImageView image = view.findViewById(R.id.image);

        image.setContentDescription(marker.getTitle());
        title.setText(marker.getTitle());
        Picasso.get()
                .load(marker.getSnippet())
                .placeholder(R.mipmap.placeholder)
                .resize(R.dimen.image_preview_width, R.dimen.image_preview_height)
                .onlyScaleDown()
                .centerCrop()
                .into(image, new MarkerCallback(marker));

        return view;

    }

    static class MarkerCallback implements Callback {
        Marker marker;

        MarkerCallback(Marker marker) {
            this.marker = marker;
        }

        @Override
        public void onError(Exception e) {
        }

        @Override
        public void onSuccess() {
            if (marker == null) {
                return;
            }

            if (!marker.isInfoWindowShown()) {
                return;
            }

            marker.hideInfoWindow();
            marker.showInfoWindow();
        }

    }

}
