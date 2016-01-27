package com.drawoverscreenandroid.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.drawoverscreenandroid.ShortService;


public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            Intent i = new Intent(context, ShortService.class);
            context.startService(i);
        }
    }
}
