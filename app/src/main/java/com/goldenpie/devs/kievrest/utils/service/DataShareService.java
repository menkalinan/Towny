package com.goldenpie.devs.kievrest.utils.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.goldenpie.devs.constanskeeper.Constants;
import com.goldenpie.devs.kievrest.KievRestApplication;
import com.goldenpie.devs.kievrest.event.NearPlacesLoadedEvent;
import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;

import java.util.ArrayList;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;


public class DataShareService extends Service implements LocationListener {

    @Inject
    protected KievRestService service;
    @Inject
    protected EventBus BUS;

    protected TeleportClient mTeleportClient;
    private LocationManager locationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        KievRestApplication.appComponent().inject(this);
        BUS.register(this);
        mTeleportClient = new TeleportClient(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mTeleportClient.setOnGetMessageTask(new MessageListener());
        if (!mTeleportClient.getGoogleApiClient().isConnected())
            mTeleportClient.connect();

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        mTeleportClient.disconnect();
        super.onDestroy();
    }

    @SuppressWarnings("unused")
    public void onEvent(NearPlacesLoadedEvent event) {
        DataMap dataMap = new DataMap();
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<String> address = new ArrayList<>();
        for (int i = 0; i < event.getResults().size(); i++) {
            ids.add(String.valueOf(event.getResults().get(i).getId()));
            labels.add(event.getResults().get(i).getTitle());
            address.add(event.getResults().get(i).getAddress());
        }
        dataMap.putStringArrayList(Constants.ID_LIST, ids);
        dataMap.putStringArrayList(Constants.LABEL_LIST, labels);
        dataMap.putStringArrayList(Constants.ADDRESS_LIST, address);

        if (mTeleportClient.getGoogleApiClient().isConnected())
            mTeleportClient.syncAll(dataMap);
    }

    public class MessageListener extends TeleportClient.OnGetMessageTask {
        @Override
        protected void onPostExecute(String path) {
            switch (path) {
                case Constants.FIND_NEAREST_PLACES:
                    getCurrentLocation();
                    break;
            }
            mTeleportClient.setOnGetMessageTask(new MessageListener());
        }
    }

    @SuppressWarnings("ResourceType")
    private void getCurrentLocation() {
        if (locationManager == null)
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (network_enabled) {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            service.loadPlacesNearMe(location.getLongitude(), location.getLatitude());
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }


    @Override
    public void onLocationChanged(Location location) {
        service.loadPlacesNearMe(location.getLongitude(), location.getLatitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
