package miadesign.hu.best.balaton.views.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import miadesign.hu.best.balaton.R;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private Context context;
    private List<String> list;

    public ImagesAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    @NonNull
    public ImagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.content_attraction_image_row, parent, false);
        return new ViewHolder(row);

    }

    @Override
    public void onBindViewHolder(@NonNull ImagesAdapter.ViewHolder viewHolder, int position) {

        Picasso.get().load(list.get(position)).into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {

        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        private ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.preview);
            imageView.setOnClickListener(preview -> {
                View container = View.inflate(context, R.layout.content_attraction_image, null);
                ImageView target = container.findViewById(R.id.image);
                Picasso.get().load(list.get(getAdapterPosition())).into(target);
                Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dialog.setContentView(container);
                dialog.show();
            });
        }

    }

}
