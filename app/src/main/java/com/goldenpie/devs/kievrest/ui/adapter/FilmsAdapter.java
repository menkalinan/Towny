package com.goldenpie.devs.kievrest.ui.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.models.FilmModel;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.ViewHolder> {

    private final LayoutInflater inflater;

    private ArrayList<FilmModel> models;
    @Getter
    private Context context;
    private int lastPosition = -1;
    @Getter
    @Setter
    private boolean hasNextPage = true;

    public FilmsAdapter(ArrayList<FilmModel> models, Context context) {
        this.context = context;
        this.models = models;
        this.inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        lastPosition = -1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.adapter_film_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FilmModel model = models.get(position);

        holder.title.setText(model.getLocaleTitle());
        holder.description.setText(model.getClearBody());

        if (model.getPoster() != null && model.getPoster().getImageUrl() != null) {
            Glide.with(getContext())
                    .load(model.getPoster().getThumbnails().getLargeThumbnail())
                    .fitCenter()
                    .placeholder(R.drawable.no_film_preview)
                    .crossFade()
                    .into(holder.poster);
        }

        holder.year.setText(String.valueOf(model.getYear()));
        if(!TextUtils.isEmpty(model.getImdbRating()) && !model.getImdbRating().equals("null"))
        holder.rating.setText(String.format("%s %S", model.getImdbRating(), model.getMpaaRating()));
        else holder.rating.setText("N/A");

        holder.duration.setText(String.format("%d %s.", model.getRunningTime(), "мин"));

        holder.adultLayout.setVisibility(View.GONE);
        if (model.getAgeRestriction() != null && model.getAgeRestriction() != 0) {
            holder.adultLayout.setVisibility(View.VISIBLE);
            holder.adult.setText(String.format("%d%s", model.getAgeRestriction(), "+"));
            if (model.getAgeRestriction() <= 12) {
                holder.adultLayout.setCardBackgroundColor(getContext().getResources().getColor(R.color.dark_green));
            } else if (model.getAgeRestriction() <= 16) {
                holder.adultLayout.setCardBackgroundColor(getContext().getResources().getColor(R.color.blue));
            } else if (model.getAgeRestriction() >= 18) {
                holder.adultLayout.setCardBackgroundColor(getContext().getResources().getColor(R.color.red));
            }
        }

        holder.itemView.setVisibility(View.VISIBLE);

        if (lastPosition < position) {
            lastPosition = position;
            YoYo.with(Techniques.FadeInUp).duration(300).playOn(holder.itemView);
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.adp_film_item_poster)
        protected ImageView poster;
        @Bind(R.id.adp_film_item_title)
        protected TextView title;
        @Bind(R.id.adp_film_item_year)
        protected TextView year;
        @Bind(R.id.adp_film_item_adult)
        protected TextView adult;
        @Bind(R.id.adp_film_item_adult_layout)
        protected CardView adultLayout;
        @Bind(R.id.adp_film_item_description)
        protected TextView description;
        @Bind(R.id.adp_film_item_rating)
        protected TextView rating;
        @Bind(R.id.adp_film_item_duration)
        protected TextView duration;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
