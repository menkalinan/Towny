package com.goldenpie.devs.keivrest.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.DelayedConfirmationView;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

import com.goldenpie.devs.kievrest.R;

public class OpenActivity extends Activity implements
        DelayedConfirmationView.DelayedConfirmationListener {

    public static final String TITLE = "title";
    private TextView title;
    private DelayedConfirmationView mDelayedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmaion);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mDelayedView = (DelayedConfirmationView) findViewById(R.id.delayed_confirm);
                title = (TextView) findViewById(R.id.title);
                title.setText(getIntent().getExtras().getString(TITLE));
                mDelayedView.setListener(OpenActivity.this);
            }
        });
                mDelayedView.setTotalTimeMs(2000);
                mDelayedView.setStartTimeMs(1000);
                mDelayedView.start();
    }

    @Override
    public void onTimerFinished(View view) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.OPEN_ON_PHONE_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, "Готово");
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onTimerSelected(View view) {
        mDelayedView.reset();
        mDelayedView.setListener(null);
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.FAILURE_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, "Отмена");
        startActivity(intent);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                OpenActivity.this.finish();
            }
        }, 1000);
    }
}
