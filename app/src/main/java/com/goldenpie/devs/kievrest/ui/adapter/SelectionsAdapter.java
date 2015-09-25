package com.goldenpie.devs.kievrest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.config.Constants;
import com.goldenpie.devs.kievrest.models.SelectionModel;
import com.squareup.picasso.Picasso;

import org.solovyev.android.views.llm.DividerItemDecoration;
import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;

public class SelectionsAdapter extends RecyclerView.Adapter<SelectionsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private int lastPosition = -1;

    private ArrayList<SelectionModel> models;
    @Getter
    private Context context;

    public SelectionsAdapter(ArrayList<SelectionModel> models, Context context) {
        this.context = context;
        this.models = models;
        this.inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.listing_adapter_item, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SelectionModel model = models.get(position);

//        holder.itemView.setVisibility(View.VISIBLE);
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());

        getDate(holder, position);

        holder.listLayout.setVisibility(View.GONE);
        if (model.getPhotos() != null) {
            holder.preview.setVisibility(View.VISIBLE);
            Picasso.with(getContext())
                    .load(model.getPhotos().get(0).getThumbnails().getLargeThumbnail())
                    .placeholder(R.drawable.no_preview_available)
                    .into(holder.preview);
        } else {
            holder.preview.setVisibility(View.GONE);
        }

        if (model.getType().equals("list")) {
            holder.listLayout.setVisibility(View.VISIBLE);
            if (model.getItems().get(0).getType().equals("place")) {
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                holder.list.setLayoutManager(layoutManager);
                holder.list.setAdapter(new SelectionPlaceAdapter(getContext(), model.getItems()));
            }
        }
//
//        if (position > lastPosition) {
//            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.up_from_bottom);
//            holder.itemView.startAnimation(animation);
//            lastPosition = position;
//        }
    }


    public static void setListViewHeightBasedOnItems(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));
        listView.setLayoutParams(params);
        listView.requestLayout();
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
        @Bind(R.id.frag_listing_item_title)
        TextView title;
        @Bind(R.id.frag_listing_item_description)
        TextView description;
        @Bind(R.id.frag_listing_item_date)
        TextView date;
        @Bind(R.id.frag_listing_item_image)
        ImageView preview;
        @Bind(R.id.frag_listing_item_list)
        RecyclerView list;
        @Bind(R.id.frag_listing_item_list_layout)
        LinearLayout listLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
