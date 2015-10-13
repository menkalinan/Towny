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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.models.CoordinatesModel;
import com.goldenpie.devs.kievrest.models.PlaceModel;
import com.google.android.gms.maps.MapView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    private final LayoutInflater inflater;

    private ArrayList<PlaceModel> models;
    @Getter
    private Context context;
    private int lastPosition = -1;
    @Getter
    @Setter
    private boolean hasNextPage = true;
    private MapView mMapView;
    private AlertDialog dialog;
    private View mapView;

    public PlacesAdapter(ArrayList<PlaceModel> models, Context context) {
        this.context = context;
        this.models = models;
        this.inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.adapter_place_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PlaceModel model = models.get(position);

        holder.title.setText(model.getFinalTitle());
        holder.description.setText(model.getDescription());

        if (model.getPhotos() != null && !model.getPhotos().isEmpty() && !TextUtils.isEmpty(model.getPhotos().get(0).getImageUrl())) {
            holder.preview.setVisibility(View.VISIBLE);
            Picasso.with(getContext())
                    .load(model.getPhotos().get(0).getThumbnails().getLargeThumbnail())
                    .placeholder(R.drawable.no_preview_available)
                    .into(holder.preview);
        } else {
            holder.preview.setVisibility(View.GONE);
        }

        holder.isOpen.setText(getContext().getString(model.isClosed() ? R.string.closed : R.string.opened));
        holder.isOpen.setBackgroundColor(getContext().getResources().getColor(model.isClosed() ? R.color.red : R.color.green));

        holder.expandLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildMapDialog(model.getCoordinates());
            }
        });

        holder.cardView.setVisibility(View.VISIBLE);

        if (lastPosition < position) {
            lastPosition = position;
            YoYo.with(Techniques.FadeInUp).duration(300).playOn(holder.cardView);
        }
    }

    private void buildMapDialog(CoordinatesModel coordinates) {
        if (mapView == null)
            mapView = View.inflate(getContext(), R.layout.dialog_map, null);

//        RelativeLayout layout = new RelativeLayout(getContext());
//        layout.setLayoutParams(new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.MATCH_PARENT));
//        GoogleMapOptions options = new GoogleMapOptions();
//        options.camera(new CameraPosition(
//                new LatLng(coordinates.getLatitude(), coordinates.getLongitude()), 15, 0, 0));
//        mMapView = new MapView(getContext(), options);
//        mMapView.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT));
//        mMapView.getMap();
//
//        layout.addView(mMapView);

        if (dialog == null)
            dialog = new AlertDialog.Builder(getContext()).setView(mapView).create();

        if (!dialog.isShowing())
            dialog.show();
//        mMapView.invalidate();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.adp_place_item_title)
        TextView title;
        @Bind(R.id.adp_place_item_description)
        TextView description;
        @Bind(R.id.adp_place_item_image)
        ImageView preview;
        @Bind(R.id.frag_place_item_is_open)
        TextView isOpen;
        @Bind(R.id.adp_place_item_card)
        CardView cardView;
        @Bind(R.id.adp_place_expand_layout)
        RelativeLayout expandLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
