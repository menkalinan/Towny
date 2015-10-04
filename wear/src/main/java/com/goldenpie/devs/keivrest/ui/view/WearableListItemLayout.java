
package com.goldenpie.devs.keivrest.ui.view;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenpie.devs.kievrest.R;

public class WearableListItemLayout extends LinearLayout
             implements WearableListView.OnCenterProximityListener {

    private TextView title;

    public WearableListItemLayout(Context context) {
        this(context, null);
    } 
 
    public WearableListItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    } 
 
    public WearableListItemLayout(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
     }

    @Override 
    protected void onFinishInflate() { 
        super.onFinishInflate();
        title = (TextView) findViewById(R.id.adp_place_item_title);
    }
 
    @Override 
    public void onCenterPosition(boolean animate) {
        title.setTextColor(getResources().getColor(R.color.primary_dark));
    }
 
    @Override 
    public void onNonCenterPosition(boolean animate) {
        title.setTextColor(getResources().getColor(R.color.primary_text));
    } 
} 