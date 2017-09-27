package com.callparent.areyoubusy.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;



public class BroadcastListener extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("test","received action!! : "+intent.getAction());

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String number = "";
        Bundle bundle = intent.getExtras();



        if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
            goServices(context,number);
        }
    }

    private void goServices(Context context, String number) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(context, CallingService.class);
        context.startService(intent);

    }
}
