package com.goldenpie.devs.keivrest.ui.adapter;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldenpie.devs.keivrest.model.PlacesModel;
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
        return new ViewHolder(inflater.inflate(R.layout.adapter_place_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        ViewHolder itemHolder = (ViewHolder) holder;

        itemHolder.address.setText(models.getAddress().get(position));
        itemHolder.label.setText(models.getLabels().get(position));

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return models.getIds().size();
    }

    public static class ViewHolder extends WearableListView.ViewHolder {
        public TextView label;
        public TextView address;
        ViewHolder(View view) {
            super(view);
            label = (TextView) view.findViewById(R.id.adp_place_item_title);
            address = (TextView) view.findViewById(R.id.adp_place_item_address);
        }
    }
}
