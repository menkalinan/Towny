package com.goldenpie.devs.keivrest.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageView;

import com.goldenpie.devs.constanskeeper.Constants;
import com.goldenpie.devs.kievrest.R;

public class ChooserActivity extends Activity implements View.OnClickListener {

    private View openPhone;
    private View openMap;
    private View makeCall;
    private ImageView callimageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                openMap = stub.findViewById(R.id.open_map);
                makeCall = stub.findViewById(R.id.make_call);
                openPhone = stub.findViewById(R.id.open_on_phone);
                callimageView = (ImageView) stub.findViewById(R.id.chooser_call_imageview);
                openPhone.setOnClickListener(ChooserActivity.this);
                openMap.setOnClickListener(ChooserActivity.this);

                //noinspection ConstantConditions
                if(getIntent().getExtras().getString(Constants.PHONE).equals("0")){
                    callimageView.setBackgroundResource(R.drawable.grey_circle_draweble);
                    makeCall.setEnabled(false);
                } else{
                    makeCall.setOnClickListener(ChooserActivity.this);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, OpenActivity.class);
        switch (view.getId()) {
            case R.id.open_map:
                i.putExtra(OpenActivity.TASK_TYPE, Constants.OPEN_MAP);
                i.putExtra(Constants.TITLE, getIntent().getExtras().getString(Constants.TITLE));
                i.putExtra(Constants.LATITUDE, getIntent().getExtras().getFloat(Constants.LATITUDE));
                i.putExtra(Constants.LONGITUDE, getIntent().getExtras().getFloat(Constants.LONGITUDE));
                break;
            case R.id.make_call:
                i.putExtra(OpenActivity.TASK_TYPE, Constants.MAKE_CALL);
                i.putExtra(Constants.PHONE, getIntent().getExtras().getString(Constants.PHONE));
                i.putExtra(Constants.TITLE, getIntent().getExtras().getString(Constants.PHONE));
                break;
            case R.id.open_on_phone:
                i.putExtra(OpenActivity.TASK_TYPE, Constants.OPEN_ACTIVITY);
                i.putExtra(Constants.TITLE, getIntent().getExtras().getString(Constants.TITLE));
                i.putExtra(Constants.ID, getIntent().getExtras().getString(Constants.ID));
                break;
        }
        startActivity(i);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ChooserActivity.this.finish();
            }
        }, 1000);
    }
}
