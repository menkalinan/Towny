package com.goldenpie.devs.keivrest.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.view.DelayedConfirmationView;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

import com.goldenpie.devs.constanskeeper.Constants;
import com.goldenpie.devs.keivrest.utils.ViewUtils;
import com.goldenpie.devs.kievrest.R;
import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;

public class OpenActivity extends Activity implements
        DelayedConfirmationView.DelayedConfirmationListener {

    public static final String TASK_TYPE = "task_type";
    private TextView title;
    private DelayedConfirmationView mDelayedView;
    private TeleportClient mTeleportClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmaion);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        mTeleportClient = new TeleportClient(this);
        mTeleportClient.connect();

        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mDelayedView = (DelayedConfirmationView) findViewById(R.id.delayed_confirm);
                title = (TextView) findViewById(R.id.title);
                title.setText(getIntent().getExtras().getString(Constants.TITLE));
                mDelayedView.setListener(OpenActivity.this);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDelayedView.setTotalTimeMs(2000);
                mDelayedView.start();
            }
        }, 1000);

    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onTimerFinished(View view) {
        if (mTeleportClient.getGoogleApiClient().isConnected()) {

            DataMap dataMap = new DataMap();

            dataMap.putString(Constants.PATH, getIntent().getExtras().getString(TASK_TYPE));

            switch (getIntent().getExtras().getString(TASK_TYPE)) {
                case Constants.OPEN_ACTIVITY:
                    dataMap.putString(Constants.ID,
                            getIntent().getExtras().getString(Constants.ID));
                    break;
                case Constants.MAKE_CALL:
                    dataMap.putString(Constants.PHONE,
                            getIntent().getExtras().getString(Constants.TITLE));
                    break;
                case Constants.OPEN_MAP:
                    dataMap.putFloat(Constants.LONGITUDE,
                            getIntent().getExtras().getFloat(Constants.LONGITUDE));
                    dataMap.putFloat(Constants.LATITUDE,
                            getIntent().getExtras().getFloat(Constants.LATITUDE));
                    dataMap.putString(Constants.TITLE,
                            getIntent().getExtras().getString(Constants.TITLE));
                    break;
            }
            mTeleportClient.syncAll(dataMap);
            ViewUtils.showSuccsessAnim(this);
        } else {
            ViewUtils.showFailureAnim(this, "Осутствует соединение");
        }
        this.finish();
    }

    @Override
    public void onTimerSelected(View view) {
        mDelayedView.reset();
        mDelayedView.setListener(null);
        ViewUtils.showFailureAnim(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                OpenActivity.this.finish();
            }
        }, 1000);
    }

    private void showSuccsessAnim() {

    }
}
