package com.goldenpie.devs.keivrest;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.view.View;

import com.goldenpie.devs.constanskeeper.Constants;
import com.goldenpie.devs.kievrest.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mariux.teleport.lib.TeleportClient;

import de.greenrobot.event.EventBus;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks {

    private WearableListView listView;
    private View loadingView;
    private TeleportClient mTeleportClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                listView = (WearableListView) stub.findViewById(R.id.list_view);
                loadingView = stub.findViewById(R.id.loading_layout);
            }
        });

        mTeleportClient = new TeleportClient(MainActivity.this);
        mTeleportClient.getGoogleApiClient().registerConnectionCallbacks(MainActivity.this);
        mTeleportClient.setOnSyncDataItemCallback(new PlacesSyncHelper());
        mTeleportClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mTeleportClient.sendMessage(Constants.FIND_NEAREST_PLACES, null);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mTeleportClient.connect();
        mTeleportClient.sendMessage(Constants.FIND_NEAREST_PLACES, null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTeleportClient.disconnect();
    }

    @SuppressWarnings("unused")
    public void onEvent(PlacesLoadedEvent event) {
        loadingView.setVisibility(View.GONE);
    }
}
