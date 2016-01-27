package com.drawoverscreenandroid;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.drawoverscreenandroid.adapter.GetSet;
import com.drawoverscreenandroid.adapter.MenuAdapter;

import java.util.ArrayList;

public class ShortService extends Service {

    public boolean showHide = false;
    WindowManager.LayoutParams params;
    MenuAdapter menuAdapter;
    ArrayList<GetSet> getSets;
    ListView listView;
    ImageButton ig;

    private WindowManager windowManager;
    private View mainLayout;
    private LinearLayout shortLayout;


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String close = intent.getStringExtra("close");
            if (Boolean.valueOf(close))
                hideMenuOnCall();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();


        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        shortLayout = new LinearLayout(this);
        shortLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout = CView(this);
        getSets = new ArrayList<GetSet>();

        /* Add your data here to show in list*/
        GetSet data1 = new GetSet();
        data1.setName("Aneh");
        getSets.add(data1);

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        ig = (ImageButton) mainLayout.findViewById(R.id.ScrollButton);
        ig.setFocusable(true);

        ig.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        if ((Math.abs(initialTouchX - event.getRawX()) < 5) && (Math.abs(initialTouchY - event.getRawY()) < 5)) {
                            hideShowMenus(mainLayout);
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX
                                + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY
                                + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(shortLayout, params);
                        return true;
                }
                return false;
            }
        });

        shortLayout.addView(mainLayout);
        windowManager.addView(shortLayout, params);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("CloseContactList"));

    }

    private View CView(Context context) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.sub_menus_layout, null);
        return view;
    }

    private void hideShowMenus(View view) {
        LinearLayout menus = (LinearLayout) view.findViewById(R.id.subMenus);

        if (showHide) {
            menus.setVisibility(View.GONE);
            showHide = false;
        } else {
            generateMenu(mainLayout);
            menus.setVisibility(View.VISIBLE);
            showHide = true;
        }
    }

    public void hideMenuOnCall() {
        hideShowMenus(mainLayout);
    }

    public void generateMenu(View view) {
        listView = (ListView) view.findViewById(R.id.menuList);
        menuAdapter = new MenuAdapter(getSets, getApplicationContext());
        listView.setAdapter(menuAdapter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (shortLayout != null)
            windowManager.removeView(shortLayout);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
