package com.goldenpie.devs.kievrest.utils.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;

import com.goldenpie.devs.constanskeeper.Constants;
import com.goldenpie.devs.kievrest.TownyApplication;
import com.goldenpie.devs.kievrest.event.NearPlacesLoadedEvent;
import com.goldenpie.devs.kievrest.event.NetworkErrorEvent;
import com.goldenpie.devs.kievrest.ui.activity.MainActivity;
import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;


public class DataShareService extends Service implements LocationListener {

    @Inject
    protected TownyService service;
    @Inject
    protected EventBus BUS;

    protected TeleportClient mTeleportClient;
    private LocationManager locationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        TownyApplication.appComponent().inject(this);
        BUS.register(this);
        mTeleportClient = new TeleportClient(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mTeleportClient.setOnSyncDataItemTask(new DataListener());
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

    @SuppressWarnings("UnusedParameters")
    public void onEvent(NetworkErrorEvent errorEvent) {
        DataMap dataMap = new DataMap();
        dataMap.putString(Constants.ERROR, Constants.NETWORK_ERROR);
        dataMap.putLong("time_stamp", new Date().getTime());

        if (mTeleportClient.getGoogleApiClient().isConnected())
            mTeleportClient.syncAll(dataMap);
    }

    @SuppressWarnings("unused")
    public void onEvent(NearPlacesLoadedEvent event) {
        DataMap dataMap = new DataMap();
        if (event.getResults() != null && !event.getResults().isEmpty()) {
            ArrayList<String> ids = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();
            ArrayList<String> address = new ArrayList<>();
            ArrayList<String> phones = new ArrayList<>();

            float[] longitudes = new float[event.getResults().size()];
            float[] latitude = new float[event.getResults().size()];

            for (int i = 0; i < event.getResults().size(); i++) {
                ids.add(String.valueOf(event.getResults().get(i).getId()));
                labels.add(event.getResults().get(i).getFinalTitle());
                address.add(event.getResults().get(i).getAddress());

                String phone = event.getResults().get(i).getPhone();
                if (!TextUtils.isEmpty(phone)) {
                    if (phone.contains(","))
                        phones.add(phone.substring(0, phone.indexOf(",")));
                    else phones.add(phone);
                } else
                    phones.add("0");

                longitudes[i] = event.getResults().get(i).getCoordinates().getLongitude();
                latitude[i] = event.getResults().get(i).getCoordinates().getLatitude();
            }

            dataMap.putStringArrayList(Constants.ID_LIST, ids);
            dataMap.putStringArrayList(Constants.LABEL_LIST, labels);
            dataMap.putStringArrayList(Constants.ADDRESS_LIST, address);
            dataMap.putStringArrayList(Constants.PHONE, phones);
            dataMap.putFloatArray(Constants.LONGITUDE, longitudes);
            dataMap.putFloatArray(Constants.LATITUDE, latitude);
        } else {
            dataMap.putString(Constants.ERROR, Constants.NO_PLACES_ERROR);
        }

        dataMap.putLong("time_stamp", new Date().getTime());

        if (mTeleportClient.getGoogleApiClient().isConnected())
            mTeleportClient.syncAll(dataMap);
    }

    private void openMap(float longitude, float latitude, String label) {
        String uri = String.format(Locale.ENGLISH, "geo:%s,%s?q=%s,%s (%s)",
                String.valueOf(latitude).replace(",", "."),
                String.valueOf(longitude).replace(",", "."),
                String.valueOf(latitude).replace(",", "."),
                String.valueOf(longitude).replace(",", "."),
                label);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }

    @SuppressWarnings("ResourceType")
    private void makeCall(String string) {
        string = string.replaceAll("\\D", "");
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + string));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }

    private void openActivity(String string) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(i);
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

    public class DataListener extends TeleportClient.OnSyncDataItemTask {

        @Override
        protected void onPostExecute(DataMap result) {
            if (result.containsKey(Constants.PATH)) {
                switch (result.getString(Constants.PATH)) {
                    case Constants.FIND_NEAREST_PLACES:
                        getCurrentLocation();
                        break;
                    case Constants.OPEN_ACTIVITY:
                        openActivity(result.getString(Constants.ID));
                        break;
                    case Constants.MAKE_CALL:
                        makeCall(result.getString(Constants.PHONE));
                        break;
                    case Constants.OPEN_MAP:
                        openMap(result.getFloat(Constants.LONGITUDE),
                                result.getFloat(Constants.LATITUDE),
                                result.getString(Constants.TITLE));
                        break;
                }
            }

            mTeleportClient.setOnSyncDataItemTask(new DataListener());
        }
    }
}
