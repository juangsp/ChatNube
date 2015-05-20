package com.example.juan.chatnube;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Juan on 13/05/2015.
 */
public class Notificaciones extends Service implements Runnable{
    static String s= Context.NOTIFICATION_SERVICE;
    NotificationManager mNotificationManager;
    Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    long[] pattern = new long[]{1000,500,1000};
    int numero=0;
    int mensajes=0;
    Context context;
    private final int MSG_NUEVO = 1;

    public Notificaciones() {
    }

    public Notificaciones(Context context,String s) {
    this.context=context;
        this.s=s;


    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    @Override
    public void run() {

        //while (numero < 5) {
            if (ParseUser.getCurrentUser() != null) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("message");
                query.whereEqualTo("id_destinatario", ParseUser.getCurrentUser().getObjectId());
                query.addDescendingOrder("createdAt");

                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> parseObjects, ParseException e) {


                        if (e == null && parseObjects.size() > 0) {
                            mensajes = parseObjects.size();
                            handler.sendEmptyMessage(MSG_NUEVO);

                        } else {
                            handler.sendEmptyMessage(0);
                        }

                    }
                });
                try {
                    Thread.sleep((long) 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                numero++;
            }
        }
    //}


    public  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_NUEVO:
                    Notificar();
                    break;
            }
        }
    };

    public  void Notificar() {

        mNotificationManager = (NotificationManager)context.getSystemService(s) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.simbolo_infinito)
                .setContentTitle("Wayta")
                .setContentInfo("Tienes " + mensajes + " mensaje(s) nuevo(s)")
                .setSound(defaultSound)
                .setVibrate(pattern)
                .setLights(Color.BLUE, 1, 0)
                .setAutoCancel(true);


        Intent notIntent =
                new Intent(context, MainActivity2.class);
        PendingIntent contIntent =
                PendingIntent.getActivity(
                        context, 0, notIntent, 0);
        mBuilder.setContentIntent(contIntent);

        mNotificationManager.notify(1, mBuilder.build());


    }



}

