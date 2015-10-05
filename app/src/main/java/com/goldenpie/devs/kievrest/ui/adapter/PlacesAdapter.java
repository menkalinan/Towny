package com.goldenpie.devs.kievrest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.models.PlaceModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    private final LayoutInflater inflater;

    private ArrayList<PlaceModel> models;
    @Getter
    private Context context;
    private int lastPosition = -1;

    public PlacesAdapter(ArrayList<PlaceModel> models, Context context) {
        this.context = context;
        this.models = models;
        this.inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.adapter_place_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PlaceModel model = models.get(position);

        holder.title.setText(model.getFinalTitle());
        holder.description.setText(model.getDescription());

        if (model.getPhotos() != null && !model.getPhotos().isEmpty() && !TextUtils.isEmpty(model.getPhotos().get(0).getImageUrl())) {
            holder.preview.setVisibility(View.VISIBLE);
            Picasso.with(getContext())
                    .load(model.getPhotos().get(0).getThumbnails().getLargeThumbnail())
                    .placeholder(R.drawable.no_preview_available)
                    .into(holder.preview);
        } else {
            holder.preview.setVisibility(View.GONE);
        }
        holder.date.setText(model.getAddress());
        holder.isOpen.setText(getContext().getString(model.isClosed() ? R.string.closed : R.string.opened));
        holder.isOpen.setBackgroundColor(getContext().getResources().getColor(model.isClosed() ? R.color.red : R.color.green));

        holder.cardView.setVisibility(View.VISIBLE);

        if (lastPosition < position) {
            lastPosition = position;
            YoYo.with(Techniques.FadeInUp).duration(300).playOn(holder.cardView);
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.adp_place_item_title)
        TextView title;
        @Bind(R.id.adp_place_item_description)
        TextView description;
        @Bind(R.id.adp_place_item_date)
        TextView date;
        @Bind(R.id.adp_place_item_image)
        ImageView preview;
        @Bind(R.id.frag_place_item_is_open)
        TextView isOpen;
        @Bind(R.id.adp_place_item_card)
        CardView cardView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
