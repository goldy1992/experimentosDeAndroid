package com.example.mike.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by Mike on 24/11/2016.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    private MainActivity mainActivity;
    
    public MyBroadcastReceiver(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mainActivity.setWifiButtonMessage(context);
    }

    public static MyBroadcastReceiver createMyBroadcastReceiver(MainActivity activity) {
        MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver(activity);
        activity.registerReceiver(myBroadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        activity.registerReceiver(myBroadcastReceiver, new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED"));
        return myBroadcastReceiver;
    }
}
