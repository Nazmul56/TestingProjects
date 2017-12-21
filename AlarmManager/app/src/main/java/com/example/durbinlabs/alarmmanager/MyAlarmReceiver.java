package com.example.durbinlabs.alarmmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

public class MyAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    private NotificationManager notificationManager;
    private final int NOTIFICATION_ID = 1010;
    private Cursor cursor;
    private SQLiteDatabase bd;
    private String alarm,description,title;

    @Override
    public void onReceive(Context context, Intent intent) {
        //Intent i = new Intent(context, MyTestService.class);
       // context.startService(i);
        Calendar calendario = Calendar.getInstance();
        int hour, min, day, month, year;
        String cadenaF, cadenaH, system_date, system_hour;

        day = calendario.get(Calendar.DAY_OF_MONTH);
        month = calendario.get(Calendar.MONTH) + 1;
        year = calendario.get(Calendar.YEAR);
        hour = calendario.get(Calendar.HOUR_OF_DAY);
        min = calendario.get(Calendar.MINUTE);
        system_date = month + "-" + day + "-" + year + " ";
        system_hour = hour + ":" + min;
      //  DataBaseHelper admin = new DataBaseHelper(context);
        /*try {
             admin.open();

            //bd = admin.getWritableDatabase();

            if (bd != null) {
                cursor = bd.rawQuery("SELECT * FROM alarma WHERE date='" + system_date + "' AND hour= '" + system_hour + "'", null);
                if (cursor.moveToFirst()) {
                    alarm = cursor.getString(0);
                    title = cursor.getString(1);
                    description = cursor.getString(2);
                    triggerNotification(context, title + "\n" + description);
                }
            }
            bd.close();



        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        //triggerNotification(context, title + "\n" + description);
        //triggerNotification(context, "Demo" + "\n" + "Test");

        triggerActivity(context);
    }
    private void triggerActivity(Context context) {
        Log.d("TRIG", "Triggred");
        Intent intentNotification = new Intent("android.intent.category.LAUNCHER");
        intentNotification.setClassName("com.example.durbinlabs.alarmmanager", "com.example.durbinlabs.alarmmanager.AlarmActivity");
        intentNotification.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentNotification);
    }
    private void triggerNotification(Context contexto, String t) {
        Intent notificationIntent = new Intent(contexto, AlarmActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(contexto, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] pattern = new long[]{2000, 1000, 2000};

        NotificationCompat.Builder builder = new NotificationCompat.Builder(contexto);
        builder.setContentIntent(contentIntent)

                .setTicker("" )
                .setContentTitle("alarma ")
                .setContentTitle("")
                .setContentText(t)
                .setContentInfo("Info")
                .setLargeIcon(BitmapFactory.decodeResource(contexto.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true) //Cuando se pulsa la notificación ésta desaparece
                .setSound(defaultSound)
                .setVibrate(pattern);

        Notification notificacion = new NotificationCompat.BigTextStyle(builder)
                .bigText(t)
                .setBigContentTitle("Alarm For Test")
                .setSummaryText("R")
                .build();

        notificationManager = (NotificationManager) contexto.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notificacion);
    }

}
