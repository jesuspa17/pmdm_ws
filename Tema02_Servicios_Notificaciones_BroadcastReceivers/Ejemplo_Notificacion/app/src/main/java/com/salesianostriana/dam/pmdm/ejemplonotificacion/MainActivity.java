package com.salesianostriana.dam.pmdm.ejemplonotificacion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {


    ToggleButton btnPlayStop;
    Intent servicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlayStop = (ToggleButton)findViewById(R.id.buttonPlayStop);

        servicio = new Intent(MainActivity.this, NotificationService.class);
        servicio.putExtra("cancion","Leiva");

        btnPlayStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startService(servicio);
                } else {
                    stopService(servicio);
                }
            }
        });

    }


}