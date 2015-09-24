package com.goldenpie.devs.kievrest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import lombok.Getter;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private int lastPosition = -1;

    private ArrayList<NewsModel> models;
    @Getter
    private Context context;

    public NewsAdapter(ArrayList<NewsModel> models, Context context) {
        this.context = context;
        this.models = models;
        this.inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.frag_news_adapter_item, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsModel model = models.get(position);

//        holder.itemView.setVisibility(View.VISIBLE);
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());

        getDate(holder, position);

        if (!TextUtils.isEmpty(model.getPhotos().get(0).getImageUrl())) {
            holder.preview.setVisibility(View.VISIBLE);
            Picasso.with(getContext())
                    .load(model.getPhotos().get(0).getImageUrl())
                    .placeholder(R.drawable.no_preview_available)
                    .into(holder.preview);
        } else {
            holder.preview.setVisibility(View.GONE);
        }
//
//        if (position > lastPosition) {
//            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.up_from_bottom);
//            holder.itemView.startAnimation(animation);
//            lastPosition = position;
//        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    private void getDate(ViewHolder viewHolder, int position) {
        Date time = new Date(models.get(position).getPublicationDate() * 1000);
        viewHolder.date.setText(Constants.dateFormat.format(time));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.frag_news_item_title)
        TextView title;
        @Bind(R.id.frag_news_item_description)
        TextView description;
        @Bind(R.id.frag_news_item_date)
        TextView date;
        @Bind(R.id.frag_news_item_image)
        ImageView preview;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
