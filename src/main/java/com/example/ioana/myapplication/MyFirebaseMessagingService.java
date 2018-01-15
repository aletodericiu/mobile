package com.example.ioana.myapplication;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Ioana on 1/13/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("FirebaseMessService", "()()()()()()()()()()()()()()()()()()()()()From: " + remoteMessage.getFrom());
        Log.d("FirebaseMessService", "()()()()()()()()()()()()()()()()()()()()()Notification Message Body: " + remoteMessage.getNotification().getBody());
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.abc)
                        .setContentTitle("Records Application")
                        .setContentText( remoteMessage.getNotification().getBody());

        int mNotificationId = 001;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
