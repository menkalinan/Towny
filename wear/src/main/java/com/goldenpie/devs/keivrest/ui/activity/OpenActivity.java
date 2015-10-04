package com.goldenpie.devs.keivrest.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.DelayedConfirmationView;
import android.support.wearable.view.WatchViewStub;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.goldenpie.devs.constanskeeper.Constants;
import com.goldenpie.devs.keivrest.utils.ViewUtils;
import com.goldenpie.devs.kievrest.R;
import com.mariux.teleport.lib.TeleportClient;

public class OpenActivity extends Activity implements
        DelayedConfirmationView.DelayedConfirmationListener {

    public static final String TITLE = "title";
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
                title.setText(getIntent().getExtras().getString(TITLE));
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

    @Override
    public void onTimerFinished(View view) {
        if (mTeleportClient.getGoogleApiClient().isConnected()) {
            mTeleportClient.sendMessage(Constants.OPEN_ACTIVITY, null);
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
