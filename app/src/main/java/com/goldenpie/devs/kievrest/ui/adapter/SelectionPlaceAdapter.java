package com.goldenpie.devs.kievrest.ui.adapter;

import android.content.Context;
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

public class SelectionPlaceAdapter extends ArrayAdapter<ItemModel> {

    private LayoutInflater inflater;

    public SelectionPlaceAdapter(Context context, int resource, List<ItemModel> objects) {
        super(context, resource, objects);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.place_listing_adapter_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.location.setText(getItem(position).getAddress());
        viewHolder.description.setText(getItem(position).getDescription());
        viewHolder.title.setText(getItem(position).getFinalTitle());

        return convertView;
    }

    static class ViewHolder{
        @Bind(R.id.place_lisitng_adapter_item_location)
        TextView location;
        @Bind(R.id.place_lisitng_adapter_item_description)
        TextView description;
        @Bind(R.id.place_lisitng_adapter_item_title)
        TextView title;

        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
