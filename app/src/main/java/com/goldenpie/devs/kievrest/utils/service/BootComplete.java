package com.goldenpie.devs.kievrest.utils.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootComplete extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent i = new Intent(context, DataShareService.class);
            context.startService(i);
        }
    }
}
