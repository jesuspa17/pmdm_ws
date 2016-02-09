package com.dam.salesianostriana.ejercicio03_matapatov2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button btnEmpezar;
    Button btnRanking;
    Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        btnEmpezar = (Button) findViewById(R.id.btnEmpezar);
        btnRanking = (Button) findViewById(R.id.btnRanking);
        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);

        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this,RankingActivity.class);
                startActivity(i);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this,EntrarActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                SharedPreferences.Editor edit = getSharedPreferences("kill_pato",MODE_PRIVATE).edit();
                edit.clear();
                edit.apply();
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
