package com.callparent.areyoubusy;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        PushAlarm pushAlarm = new PushAlarm();
        pushAlarm.pushAlarm(context,intent.getExtras().getString("번호"),intent.getExtras().getString("이름"));

        // Toast.makeText(context, "-----", Toast.LENGTH_SHORT).show();
    }
}