package com.dam.salesianostriana.pmdm.reproductormusica;

/**
 * Created by Jesus on 31/10/2015.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public  class MiNotificacion extends Service{
    NotificationCompat.Builder mBuilder;

    //id asociada a la noti ficación
    int nNotificationId = 1;
    NotificationManager notMag = null;
    MediaPlayer mediaPlayer;

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
            String grupo = extras.getString("grupo");
            String duracion = extras.getString("duracion");
            int uriCancion = extras.getInt("uri");
            int icono = extras.getInt("icono");
            //String sing = extras.getString("uri");

            // 1. PROPIEDADES DE LA NOTIFICACIÓN
            // Pendint Intent - Acción Eliminar
            Intent accionIntent = new Intent(this, ReproductorActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, accionIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(android.R.drawable.ic_media_play)
                    .setContentTitle("Reproduciendo")
                    .setContentText(grupo + "-" +cancionAReproducir).addAction(android.R.drawable.ic_media_pause, "Pausar", pIntent)
                    .addAction(android.R.drawable.ic_media_play, "Reanudar", pIntent);

            mediaPlayer = MediaPlayer.create(this, uriCancion);

            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }else {
                mediaPlayer.start();
            }

            /** ESTO SERÍA PARA REPRODUCIR LA CANCIÓN DADA UNA URL
             * DE INTERNET, PERO NINGUNA DE LAS URL QUE LE HE PASADO VALEN.
             *
             * En el caso de que funcionaran las URL's, deberíamos cambiar en la clase Canción,
             * el valor ruta_cancion (int) por un String.
             */

            /*
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);//como tenemos implementada la clase, podemos usar un this
            mediaPlayer.setOnCompletionListener(this);//esta clase implementada es para cuando ha acabado de cargar la canción
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            try {
                mediaPlayer.setDataSource();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.prepareAsync();
            */


            // 2. ACCIÓN DE LA NOTIFICACIÓN (ACTIVITY QUE SE VA A LANZAR AL HACER
            // CLICK EN LA NOTIFICACIÓN)

            Intent resultIntent = new Intent(this, ReproductorActivity.class);


            resultIntent.putExtra("cancion",cancionAReproducir);
            resultIntent.putExtra("grupo",grupo);
            resultIntent.putExtra("duracion", duracion);
            if(mediaPlayer.isPlaying()){
                resultIntent.putExtra("icono", android.R.drawable.ic_media_pause);
            }else {
                resultIntent.putExtra("icono", android.R.drawable.ic_media_play);
            }

            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            mBuilder.setContentIntent(resultPendingIntent);


            // 3. LANZAMOS LA NOTIFICACIÓN AL AIRE (ON AIR)
            // Sets an ID for the notification
            int mNotificationId = 001;
            // Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
        }

        return START_REDELIVER_INTENT;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        //Elimina la notificación.
        notMag.cancel(nNotificationId);
        mediaPlayer.stop();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}