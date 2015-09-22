package com.goldenpie.devs.kievrest.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.config.Constants;
import com.goldenpie.devs.kievrest.models.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsAdapter extends ArrayAdapter<NewsModel> {

    private final LayoutInflater inflater;
    private int lastPosition = -1;

    public NewsAdapter(Context context, int resource, ArrayList<NewsModel> models) {
        super(context, resource, models);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.frag_news_adapter_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(getItem(position).getTitle());
        viewHolder.description.setText(getItem(position).getDescription());

        getDate(viewHolder, position);

        if (!TextUtils.isEmpty(getItem(position).getPhotos().get(0).getImageUrl())) {
            viewHolder.preview.setVisibility(View.VISIBLE);
            Picasso.with(getContext())
                    .load(getItem(position).getPhotos().get(0).getImageUrl())
                    .into(viewHolder.preview);
        } else {
            viewHolder.preview.setVisibility(View.GONE);
        }

        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.up_from_bottom);
            convertView.startAnimation(animation);
            lastPosition = position;
        }

        return convertView;
    }

    private void getDate(ViewHolder viewHolder, int position) {
        Date time = new Date(getItem(position).getPublicationDate() * 1000);
        viewHolder.date.setText(Constants.dateFormat.format(time));
    }

    static class ViewHolder {
        @Bind(R.id.frag_news_item_title)
        TextView title;
        @Bind(R.id.frag_news_item_description)
        TextView description;
        @Bind(R.id.frag_news_item_date)
        TextView date;
        @Bind(R.id.frag_news_item_image)
        ImageView preview;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
