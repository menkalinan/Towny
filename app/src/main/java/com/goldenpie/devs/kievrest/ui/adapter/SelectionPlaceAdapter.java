package com.goldenpie.devs.kievrest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.models.ItemModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectionPlaceAdapter extends RecyclerView.Adapter<SelectionPlaceAdapter.ViewHolder>  {

    private LayoutInflater inflater;
    private List<ItemModel> models;

    public SelectionPlaceAdapter(Context context, List<ItemModel> objects) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.models = objects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.place_listing_adapter_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.location.setText(getItem(position).getAddress());
        holder.description.setText(getItem(position).getDescription());
        holder.title.setText(getItem(position).getFinalTitle());
    }

    private ItemModel getItem(int position) {
        return models.get(position);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.place_lisitng_adapter_item_location)
        TextView location;
        @Bind(R.id.place_lisitng_adapter_item_description)
        TextView description;
        @Bind(R.id.place_lisitng_adapter_item_title)
        TextView title;

        ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
