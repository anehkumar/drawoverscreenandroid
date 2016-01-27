package com.drawoverscreenandroid;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final int Permission_Request = 211;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void checkDrawOverlayPermission() {
        if (!Settings.canDrawOverlays(MainActivity.this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, Permission_Request);
        }
    }


    public void startService(View view) {
        v = view;
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            // only for gingerbread and newer versions
            checkDrawOverlayPermission();
        } else {
            Intent i = new Intent(MainActivity.this, ShortService.class);
            startService(i);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Permission_Request) {
            if (Settings.canDrawOverlays(MainActivity.this)) {
                // continue here - permission was granted
                if (v != null)
                    startService(v);
            }
        }
    }

    public void stopService(View view) {
        Intent i = new Intent(MainActivity.this, ShortService.class);
        stopService(i);
    }
}
