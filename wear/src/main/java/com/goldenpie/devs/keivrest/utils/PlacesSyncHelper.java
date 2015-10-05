package com.goldenpie.devs.keivrest.utils;

import com.goldenpie.devs.constanskeeper.Constants;
import com.goldenpie.devs.keivrest.event.PlacesLoadedEvent;
import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;

import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;
import lombok.Getter;
import lombok.Setter;

public class PlacesSyncHelper extends TeleportClient.OnSyncDataItemTask {

    @Getter
    @Setter
    private TeleportClient teleportClient;

    public PlacesSyncHelper(TeleportClient mTeleportClient) {
        setTeleportClient(mTeleportClient);
    }

    @DebugLog
    @Override
    protected void onPostExecute(DataMap result) {
        if (!result.containsKey(Constants.PATH)) {
            PlacesLoadedEvent placesLoadedEvent = new PlacesLoadedEvent();
            placesLoadedEvent.setIds(result.getStringArrayList(Constants.ID_LIST));
            placesLoadedEvent.setLabels(result.getStringArrayList(Constants.LABEL_LIST));
            placesLoadedEvent.setAddress(result.getStringArrayList(Constants.ADDRESS_LIST));
            placesLoadedEvent.setPhones(result.getStringArrayList(Constants.PHONE));
            placesLoadedEvent.setLatitudes(result.getFloatArray(Constants.LATITUDE));
            placesLoadedEvent.setLongitudes(result.getFloatArray(Constants.LONGITUDE));

            EventBus.getDefault().post(placesLoadedEvent);
        }

        getTeleportClient().setOnSyncDataItemTask(new PlacesSyncHelper(getTeleportClient()));
    }
}
