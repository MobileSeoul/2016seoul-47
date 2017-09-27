package com.callparent.areyoubusy.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.CallLog;
import android.support.v4.content.ContentResolverCompat;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import com.callparent.areyoubusy.AlarmReceiver;
import com.callparent.areyoubusy.CallDetails;
import com.callparent.areyoubusy.Dday;
import com.callparent.areyoubusy.MainActivity;

import java.util.Date;

import static com.callparent.areyoubusy.MainActivity.db;
import static com.callparent.areyoubusy.ProfileSettingActivity.pushhash;


public class CallingService extends Service {
    String call_number;
    Cursor managedCursor;
    Context context;
    int call_duration;
    Date call_date;
    Dday dday;

    @Override
    public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            managedCursor =
                    ContentResolverCompat.query(
                            this.getApplication().getContentResolver(),
                            CallLog.Calls.CONTENT_URI,
                            null,
                            null,
                            null,
                            null,
                            null);
            managedCursor.moveToNext();
            call_number = managedCursor.getString(managedCursor.getColumnIndex(CallLog.Calls.NUMBER));
            call_duration = managedCursor.getInt(managedCursor.getColumnIndex(CallLog.Calls.DURATION));
            call_date = new Date(Long.valueOf(managedCursor.getString(managedCursor.getColumnIndex(CallLog.Calls.DATE))));
            dday = new Dday();

            if (call_duration == 0 || db.getSelectName(call_number) == null) {

            } else {

                db.update(db.getSelectName(call_number),
                        call_number,
                        db.getSelectBirth(call_number),
                        db.getSelectWedding(call_number),
                        db.getSelectVolume(call_number) + call_duration,
                        db.getSelectCount(call_number) + 1,
                        dday.caldate2(call_date));


            }


          /*  String phone = call_number;
            int pushvalue = pushhash.get(call_number);

            AlarmManager mAlarmMgr;
            mAlarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

            if (pushvalue == 0) {
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                // 알람 중지
                mAlarmMgr.cancel(pIntent);
            } else {
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                // 알람 중지
                mAlarmMgr.cancel(pIntent);

                intent.putExtra("번호", phone);
                intent.putExtra("이름", db.getSelectName(call_number));
                pIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                long diff = (pushvalue - dday.caldate2(call_date)) * (24 * 60 * 60 * 1000);

                if (diff < 0) {
                    diff = 0;
                }
                mAlarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, diff, 24 * 60 * 60 * 1000, pIntent);
            }*/
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"잘못된 접근입니다",Toast.LENGTH_LONG).show();
        }
        //db.
        //db.update(call)

    }
    public int onStartCommand(Intent intent, int flags, int startId)
    {


        stopService(intent);
        return startId;
    }
}