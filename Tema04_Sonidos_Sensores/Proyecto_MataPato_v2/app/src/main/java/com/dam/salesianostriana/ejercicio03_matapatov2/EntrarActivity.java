package com.dam.salesianostriana.ejercicio03_matapatov2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Response;

public class EntrarActivity extends AppCompatActivity {

    Button entrar;
    EditText nickName;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);
        getSupportActionBar().hide();

        //Elementos de la UI
        entrar = (Button) findViewById(R.id.btnJugar);
        nickName = (EditText) findViewById(R.id.editTextNickname);

        //Inicializo las preferencias
        preferences = getSharedPreferences("kill_pato", MODE_PRIVATE);
        editor = preferences.edit();


        //Si el campo nick de las preferncias
        if(preferences.getString("nick",null)!=null){
            startActivity(new Intent(EntrarActivity.this, MenuActivity.class));
            this.finish();
        }

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nick = nickName.getText().toString();
                new RegistroTask().execute(nick);
                Intent i = new Intent(EntrarActivity.this,MenuActivity.class);
                startActivity(i);
                EntrarActivity.this.finish();

            }
        });
    }

    public class RegistroTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            Call<ArrayList<String>> call = Servicio.instanciarServicio().registrarse(params[0]);
            Response<ArrayList<String>> response = null;

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
                editor.putString("nick", nickName.getText().toString());
                editor.apply();
            }
            super.onPostExecute(code);
        }
    }
}
