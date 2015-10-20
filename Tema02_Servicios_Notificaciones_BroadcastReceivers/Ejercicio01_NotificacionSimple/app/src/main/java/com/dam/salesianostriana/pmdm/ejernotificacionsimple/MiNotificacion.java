package com.dam.salesianostriana.pmdm.ejernotificacionsimple;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public class MiNotificacion extends Service {
    NotificationCompat.Builder mBuilder;

    //id asociada a la notificación
    int nNotificationId = 1;
    NotificationManager notMag = null;

    public MiNotificacion() {
    }

    // 1. Primer método que se lanza cuando se crea un Servicio
    @Override
    public void onCreate() {
        super.onCreate();
        //gestiona la notificación
        notMag = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Rescatar datos del intent
        if(intent!=null) {
            Bundle extras = intent.getExtras();
            String cancionAReproducir = extras.getString("cancion");

            mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.start).setContentTitle("Reproduciendo").setContentText(cancionAReproducir);
            //se crea la notificación
            notMag.notify(nNotificationId, mBuilder.build());
        }

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBuilder.setSmallIcon(R.drawable.pau);
        mBuilder.setContentTitle("Pausa");
        //actualizo notificacion
        notMag.notify(nNotificationId, mBuilder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



}
