package com.goldenpie.devs.kievrest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.config.Constants;
import com.goldenpie.devs.kievrest.models.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final LayoutInflater inflater;

    private ArrayList<NewsModel> models;
    @Getter
    private Context context;
    private int lastPosition = -1;

    @Getter
    @Setter
    private boolean hasNextPage = true;

    public NewsAdapter(ArrayList<NewsModel> models, Context context) {
        this.context = context;
        this.models = models;
        this.inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.adapter_news_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsModel model = models.get(position);

        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());

        getDate(holder, position);

        if (model.getPhotos() != null && !TextUtils.isEmpty(model.getPhotos().get(0).getImageUrl())) {
            holder.preview.setVisibility(View.VISIBLE);
            Picasso.with(getContext())
                    .load(model.getPhotos().get(0).getThumbnails().getLargeThumbnail())
                    .placeholder(R.drawable.no_preview_available)
                    .into(holder.preview);
        } else {
            holder.preview.setVisibility(View.GONE);
        }
        if (model.getPlace() != null) {
            holder.placeLayout.setVisibility(View.VISIBLE);
            holder.locationDescription.setText(model.getPlace().getFinalTitle());
            holder.locationPlace.setText(model.getPlace().getAddress());
        } else {
            holder.placeLayout.setVisibility(View.GONE);
        }

        holder.cardView.setVisibility(View.VISIBLE);

        if (lastPosition < position) {
            lastPosition = position;
            YoYo.with(Techniques.FadeInUp).duration(350).playOn(holder.cardView);
        }
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
        @Bind(R.id.adp_news_item_title)
        TextView title;
        @Bind(R.id.adp_news_item_description)
        TextView description;
        @Bind(R.id.adp_news_item_date)
        TextView date;
        @Bind(R.id.adp_news_item_image)
        ImageView preview;
        @Bind(R.id.adp_news_item_location)
        LinearLayout placeLayout;
        @Bind(R.id.adp_news_item_location_description)
        TextView locationDescription;
        @Bind(R.id.adp_news_item_location_place)
        TextView locationPlace;
        @Bind(R.id.adp_news_item_card)
        CardView cardView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
