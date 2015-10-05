
package com.goldenpie.devs.keivrest.ui.view;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenpie.devs.kievrest.R;

public class WearableListItemLayout extends FrameLayout
             implements WearableListView.OnCenterProximityListener {

    private TextView title;
    private TextView descriprion;

    public WearableListItemLayout(Context context) {
        super(context);
        init(context);
    } 

    private void init(Context context) {
        View.inflate(context, R.layout.adapter_place_item, this);
        title = (TextView) findViewById(R.id.adp_place_item_address);
        descriprion = (TextView) findViewById(R.id.adp_place_item_title);
    }


    @Override 
    protected void onFinishInflate() { 
        super.onFinishInflate();
        title = (TextView) findViewById(R.id.adp_place_item_title);
    }
 
    @Override 
    public void onCenterPosition(boolean animate) {
//        title.setTextColor(getResources().getColor(R.color.primary_dark));
        title.animate().scaleX(1f).scaleY(1f).alpha(1);
        descriprion.animate().scaleX(1f).scaleY(1f).alpha(1);
    }
 
    @Override 
    public void onNonCenterPosition(boolean animate) {
//        title.setTextColor(getResources().getColor(R.color.primary_text));
        title.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
        descriprion.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
    } 
} 