package com.dam.salesianostriana.ejercicio03_matapatov2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;

public class EntrarActivity extends AppCompatActivity {
    Button entrar;
    TextView nickName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);

        getSupportActionBar().hide();

        entrar = (Button) findViewById(R.id.btn_entrar);
        nickName = (TextView) findViewById(R.id.textViewNickName);


        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EntrarActivity.this,MenuActivity.class);
                startActivity(i);
                new RegistroTask().execute("jesuspa");
            }
        });
    }

    public class RegistroTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            Call<String> call = Servicio.instanciarServicio().registrarse(params[0]);
            Response<String> response = null;

            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response.code();
        }

        @Override
        protected void onPostExecute(Integer code) {
            if (code == 404) {
                Toast.makeText(EntrarActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EntrarActivity.this, "Registro completado", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(code);
        }
    }
}
