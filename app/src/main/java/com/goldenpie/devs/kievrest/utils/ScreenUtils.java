package com.goldenpie.devs.kievrest.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;

public class ScreenUtils {
    public static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    public static int getDisplayColumns(Activity activity) {
        int columnCount = 1;
        if (isTablet(activity) || activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columnCount = 2;
            if(isTablet(activity) && activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                columnCount = 3;
            }
        }
        return columnCount;
    }
}
