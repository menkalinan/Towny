package com.goldenpie.devs.keivrest.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.view.WindowManager;

import com.goldenpie.devs.constanskeeper.Constants;
import com.goldenpie.devs.keivrest.event.ErrorEvent;
import com.goldenpie.devs.keivrest.event.PlacesLoadedEvent;
import com.goldenpie.devs.keivrest.ui.adapter.PlacesAdapter;
import com.goldenpie.devs.keivrest.utils.PlacesSyncHelper;
import com.goldenpie.devs.keivrest.utils.ViewUtils;
import com.goldenpie.devs.kievrest.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;

import java.util.Date;

import de.greenrobot.event.EventBus;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, WearableListView.ClickListener {

    private WearableListView listView;
    private View loadingView;
    private View successView;

    private TeleportClient mTeleportClient;

    private PlacesAdapter adapter;

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

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mTeleportClient = new TeleportClient(MainActivity.this);
        mTeleportClient.getGoogleApiClient().registerConnectionCallbacks(MainActivity.this);
        mTeleportClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mTeleportClient.setOnSyncDataItemTask(new PlacesSyncHelper(mTeleportClient));
        DataMap dataMap = new DataMap();
        dataMap.putString(Constants.PATH, Constants.FIND_NEAREST_PLACES);
        dataMap.putLong("time_stamp", new Date().getTime());
        mTeleportClient.syncAll(dataMap);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mTeleportClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTeleportClient.disconnect();
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @SuppressWarnings("unused")
    public void onEvent(ErrorEvent errorEvent) {
        switch (errorEvent.getError()) {
            case Constants.NETWORK_ERROR:
                ViewUtils.showFailureAnim(this, "Отсутсвует соединение");
                break;
            case Constants.UNKNOWN_ERROR:
                ViewUtils.showFailureAnim(this, "Ошыбка сервера");
                break;
            case Constants.NO_PLACES_ERROR:
                ViewUtils.showFailureAnim(this, "Поблизости нету интересных мест");
                break;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.finish();
            }
        }, 1000);
    }

    @SuppressWarnings("unused")
    public void onEvent(PlacesLoadedEvent event) {
        if (adapter == null) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            populateData(event);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingView.setVisibility(View.GONE);
                        }
                    }, 1000);

                    ViewUtils.showSuccsessAnim(MainActivity.this, "Места загружены");
                }
            }, 1000);
        }
    }

    private void populateData(PlacesLoadedEvent event) {
        if (adapter == null)
            adapter = new PlacesAdapter(event, getApplicationContext());

        listView.setAdapter(adapter);
        listView.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder v) {
        Integer position = (Integer) v.itemView.getTag();
        Intent intent = new Intent(MainActivity.this, ChooserActivity.class);
        intent.putExtra(Constants.TITLE, adapter.getModels().getLabels().get(position));
        intent.putExtra(Constants.LONGITUDE, adapter.getModels().getLongitudes()[position]);
        intent.putExtra(Constants.LATITUDE, adapter.getModels().getLatitudes()[position]);
        intent.putExtra(Constants.PHONE, adapter.getModels().getPhones().get(position));
        startActivity(intent);
    }

    @Override
    public void onTopEmptyRegionClick() {
    }
}
