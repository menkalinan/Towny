package com.goldenpie.devs.keivrestwear;

import android.app.Activity;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.view.View;

import com.goldenpie.devs.constanskeeper.Constants;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mariux.teleport.lib.TeleportClient;

public class MainActivity extends Activity {

    private WearableListView listView;
    private View loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                listView = (WearableListView) stub.findViewById(R.id.list_view);
                loadingView = stub.findViewById(R.id.loading_layout);
            }
        });
        final TeleportClient mTeleportClient = new TeleportClient(getApplicationContext());
        mTeleportClient.getGoogleApiClient().registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle bundle) {
                mTeleportClient.sendMessage(Constants.FIND_NEAREST_PLACES, null);
            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        });
        mTeleportClient.setOnSyncDataItemCallback(new PlacesSyncHelper(mTeleportClient));
        mTeleportClient.connect();
    }
}
