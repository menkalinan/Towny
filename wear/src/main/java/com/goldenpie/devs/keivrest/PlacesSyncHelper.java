package com.goldenpie.devs.keivrest;

import com.goldenpie.devs.constanskeeper.Constants;
import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;

import de.greenrobot.event.EventBus;

public class PlacesSyncHelper extends TeleportClient.OnSyncDataItemCallback {

    @Override
    public void onDataSync(DataMap dataMap) {
        PlacesLoadedEvent placesLoadedEvent = new PlacesLoadedEvent();
        placesLoadedEvent.setIds(dataMap.getStringArrayList(Constants.ID_LIST));
        placesLoadedEvent.setLabels(dataMap.getStringArrayList(Constants.LABEL_LIST));
        placesLoadedEvent.setAddress(dataMap.getStringArrayList(Constants.ADDRESS_LIST));
        EventBus.getDefault().post(placesLoadedEvent);
    }
}
