package com.goldenpie.devs.kievrest.ui.adapter;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.TownyApplication;
import com.goldenpie.devs.kievrest.models.CoordinatesModel;
import com.goldenpie.devs.kievrest.models.PlaceModel;
import com.goldenpie.devs.kievrest.ui.fragment.MapDialog;
import com.goldenpie.devs.kievrest.utils.DistanceUtils;
import com.goldenpie.devs.kievrest.utils.service.ApplicationPreferences;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    private final LayoutInflater inflater;

    @Inject
    protected ApplicationPreferences preferences;

    private ArrayList<PlaceModel> models;
    @Getter
    private Context context;
    private int lastPosition = -1;
    @Getter
    @Setter
    private boolean hasNextPage = true;
    private MapDialog mapDialog;
    private int status = -1;
    private LocationManager locationManager;
    private Location location;

    public PlacesAdapter(ArrayList<PlaceModel> models, Context context) {
        TownyApplication.appComponent().inject(this);
        this.context = context;
        this.models = models;
        this.inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (locationManager == null)
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (location == null)
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
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

        if (model.getPhotos() != null && !model.getPhotos().isEmpty()
                && !TextUtils.isEmpty(model.getPhotos().get(0).getImageUrl())) {
            Glide.with(getContext())
                    .load(model.getPhotos().get(0).getThumbnails().getLargeThumbnail())
                    .placeholder(R.drawable.no_photo_preview)
                    .crossFade()
                    .into(holder.preview);
        }

        holder.isOpen.setText(getContext().getString(model.isClosed() ? R.string.closed : R.string.opened));
        holder.isOpen.setTextColor(getContext().getResources().getColor(model.isClosed() ? R.color.red : R.color.dark_green));

        holder.expandLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildMapDialog(model.getCoordinates(), model.getAddress());
            }
        });

        try {
            holder.distanceLayout.setVisibility(View.VISIBLE);
            holder.distance.setText(String.format("~%.2f" + getUnit(), DistanceUtils.getDistance(
                    model.getCoordinates().getLatitude(), model.getCoordinates().getLongitude(),
                    location.getLatitude(), location.getLongitude(), preferences.getUnits())).replace(",", "."));
        } catch (NullPointerException e) {
            e.printStackTrace();
            holder.distanceLayout.setVisibility(View.GONE);
        }

        holder.cardView.setVisibility(View.VISIBLE);

        if (lastPosition < position) {
            lastPosition = position;
            YoYo.with(Techniques.FadeInUp).duration(300).playOn(holder.cardView);
        }
    }

    private void buildMapDialog(CoordinatesModel coordinates, String address) {
        if (status == -1)
            status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());

        if (status == ConnectionResult.SUCCESS) {
            if (mapDialog == null)
                mapDialog = MapDialog.newInstance();

            mapDialog.setLatLng(coordinates.getCoordinates());
            mapDialog.setLabel(address);
            mapDialog.show(((AppCompatActivity) getContext()).getSupportFragmentManager(), "Map");

        } else {
            GooglePlayServicesUtil.getErrorDialog(status, (AppCompatActivity) getContext(), status).show();
        }

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public String getUnit() {
        return (preferences.getUnits() == DistanceUtils.KILOMETRE
                ? context.getString(R.string.km) : context.getString(R.string.mile));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.adp_film_item_title)
        TextView title;
        @Bind(R.id.adp_place_item_distance)
        TextView distance;
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
        @Bind(R.id.adp_place_item_distance_layout)
        RelativeLayout distanceLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
