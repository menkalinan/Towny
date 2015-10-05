package com.goldenpie.devs.keivrest.ui.adapter;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldenpie.devs.keivrest.model.PlacesModel;
import com.goldenpie.devs.keivrest.ui.view.PlaceItemLayout;
import com.goldenpie.devs.kievrest.R;

import lombok.Getter;

public class PlacesAdapter extends WearableListView.Adapter {

    private final LayoutInflater inflater;

    @Getter
    private PlacesModel models;
    @Getter
    private Context context;

    public PlacesAdapter(PlacesModel models, Context context) {
        this.context = context;
        this.models = models;
        this.inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new WearableListView.ViewHolder(new PlaceItemLayout(getContext()));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        PlaceItemLayout view = (PlaceItemLayout) holder.itemView;

        TextView label = (TextView) view.findViewById(R.id.adp_place_item_title);
        TextView address = (TextView) view.findViewById(R.id.adp_place_item_address);

        address.setText(models.getAddress().get(position));
        label.setText(models.getLabels().get(position));

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return models.getIds().size();
    }

}
