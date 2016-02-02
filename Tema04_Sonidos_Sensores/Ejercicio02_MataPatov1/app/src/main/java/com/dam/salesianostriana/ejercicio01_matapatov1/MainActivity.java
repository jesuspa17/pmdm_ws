package com.dam.salesianostriana.ejercicio01_matapatov1;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    SoundPool soundPool;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Se crea el objeto q quiero insertar
        final ImageView pato = new ImageView(this);
        //le doy características
        pato.setImageResource(R.drawable.duck);


        //lo añado al layout
        RelativeLayout mlayout = new RelativeLayout(this);
        mlayout.addView(pato);

        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) pato.getLayoutParams();
        params.width = 100;
        params.setMargins(0, 0, 0, 0);
        pato.setLayoutParams(params);

        setContentView(mlayout);


        AudioAttributes aa = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(aa)
                .build();

        id = soundPool.load(MainActivity.this, R.raw.powerup, 1);


        pato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DisplayMetrics displaymetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                int width = displaymetrics.widthPixels;
                int height = displaymetrics.heightPixels;

                int buttonWidth = pato.getWidth();
                int buttonHeight = pato.getHeight();

                final Random r = new Random();

                pato.setX(r.nextInt(width - buttonWidth));
                pato.setY(r.nextInt(height - buttonHeight));
                pato.setZ(r.nextInt(500));


                soundPool.play(id, 1, 1, 1, 0, 1f);


            }
        });


    }
}
