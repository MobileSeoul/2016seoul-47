package com.callparent.areyoubusy;


import android.app.NotificationManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;


public class PushAlarm {

    public void pushAlarm(Context context,String phone,String name) {
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(context.getApplicationContext(),MainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( context,0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        callIntent.setData(Uri.parse("tel:"+phone));
        PendingIntent callPendingIntent = PendingIntent.getActivity(context, 0, callIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.profile_image)
                .addAction(R.drawable.callbutton_mini, "전화걸기", callPendingIntent)
                .setTicker("NOTIFICATION")
                .setWhen(System.currentTimeMillis())
                .setStyle(new NotificationCompat.InboxStyle())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingNotificationIntent)
                .setContentTitle("연락알람")
                .setContentText(name+"이(가) 연락을 기다리고 있어요")
                .build();
        notificationManager.notify(1, notification);
    }
}