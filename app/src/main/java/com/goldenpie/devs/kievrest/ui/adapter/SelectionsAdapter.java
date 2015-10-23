package com.goldenpie.devs.kievrest.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.models.SelectionModel;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

public class SelectionsAdapter extends RecyclerView.Adapter<SelectionsAdapter.ViewHolder> {

    private final LayoutInflater inflater;

    private ArrayList<SelectionModel> models;
    @Getter
    private Context context;
    private int lastPosition = -1;

    @Getter
    @Setter
    private boolean hasNextPage = true;

    private HashMap<Integer, SelectionPlaceAdapter> placeAdapterHashMap = new HashMap<>();

    public SelectionsAdapter(ArrayList<SelectionModel> models, Context context) {
        this.context = context;
        this.models = models;
        this.inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.adapter_listing_item, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SelectionModel model = models.get(position);

//        holder.itemView.setVisibility(View.VISIBLE);
        holder.title.setText(model.getTitle());
        if(!TextUtils.isEmpty(model.getDescription()))
            holder.description.setText(model.getClearDescription());

//        getDate(holder, position);

        holder.listLayout.setVisibility(View.GONE);
        if (model.getPhotos() != null) {
            holder.preview.setVisibility(View.VISIBLE);
            Glide.with(getContext())
                    .load(model.getPhotos().get(0).getThumbnails().getLargeThumbnail())
                    .placeholder(R.drawable.no_preview_available)
                    .crossFade()
                    .into(holder.preview);
        } else {
            holder.preview.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(model.getType()) && model.getType().equals("list")) {
            if (model.getItems().get(0).getType().equals("place")) {
                setInnerListLogic(holder, position, model);
            }
        }

        holder.cardView.setVisibility(View.VISIBLE);

        if (lastPosition < position) {
            lastPosition = position;
            YoYo.with(Techniques.FadeInUp).duration(350).playOn(holder.cardView);
        }
    }

    private void setInnerListLogic(final ViewHolder holder, final int position, final SelectionModel model) {
        holder.expandLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!placeAdapterHashMap.containsKey(position)) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    holder.list.setLayoutManager(layoutManager);
                    SelectionPlaceAdapter placeAdapter = new SelectionPlaceAdapter(getContext(), model.getItems());
                    placeAdapterHashMap.put(position, placeAdapter);
                    holder.list.setAdapter(placeAdapter);
                } else {
                    holder.list.setAdapter(placeAdapterHashMap.get(position));
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (holder.listLayout.getVisibility() == View.GONE) {
                            holder.listLayout.setVisibility(View.VISIBLE);
                            YoYo.with(Techniques.FadeIn).duration(200).playOn(holder.listLayout);
                            holder.expandArrow.setRotation(0);
                            holder.expandText.setText("Свернуть");
                        } else {
                            YoYo.with(Techniques.FadeOut).duration(200).playOn(holder.listLayout);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    holder.listLayout.setVisibility(View.GONE);
                                    holder.expandArrow.setRotation(270);
                                    holder.expandText.setText("Развернуть");
                                }
                            }, 200);
                        }
                    }
                }, 50);

            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

//    private void getDate(ViewHolder viewHolder, int position) {
//        Date time = new Date(models.get(position).getPublicationDate() * 1000);
//        viewHolder.date.setText(Constants.dateFormat.format(time));
//    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.adp_selection_item_title)
        TextView title;
        @Bind(R.id.adp_selection_item_description)
        TextView description;
        @Bind(R.id.adp_selection_item_image)
        ImageView preview;
        @Bind(R.id.adp_selection_item_list)
        RecyclerView list;
        @Bind(R.id.adp_selection_item_list_layout)
        LinearLayout listLayout;
        @Bind(R.id.adp_selection_item_card)
        CardView cardView;


        @Bind(R.id.adp_selection_expand_arrow)
        ImageView expandArrow;
        @Bind(R.id.adp_selection_expand_layout)
        RelativeLayout expandLayout;
        @Bind(R.id.adp_selection_expand_text)
        TextView expandText;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
