package com.salesianostriana.dam.pmdm.ejemplonotificacion;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class NotificationService extends Service {
    NotificationCompat.Builder mBuilder;

    //id asociada a la notificación
    int nNotificationId = 1;
    NotificationManager notMag = null;

    public NotificationService() {
    }

    // 1. Primer método que se lanza cuando se crea un Servicio
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("SERVICIO", "SERVICIO: método onCreate()");

        //gestiona la notificación
        notMag = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SERVICIO","SERVICIO: método onStartCommand()");

        // Rescatar datos del intent
        if(intent!=null) {
            Bundle extras = intent.getExtras();
            String cancionAReproducir = extras.getString("cancion");
            Log.i("SERVICIO", "SERVICIO: Reproduciendo " + cancionAReproducir);

            mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.msg).setContentTitle("Reproduciendo").setContentText(cancionAReproducir);


            //se crea la notificación
            notMag.notify(nNotificationId, mBuilder.build());
        }



        // Si se cancela el servicio no se relanza.
        // return START_NOT_STICKY;

        // Si se cancela el servicio se relanza con Intent nulo
        //return START_STICKY;

        // Si se cancela el servicio y quiero que lance el último Intent
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SERVICIO", "SERVICIO: método onDestroy()");

        mBuilder.setContentTitle("Reproductor Parado");
        mBuilder.setContentText("Ninguna");
        //actualizo notificacion
        notMag.notify(nNotificationId, mBuilder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}