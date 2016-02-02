package com.dam.salesianostriana.calccalorias;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.salesianostriana.calccalorias.greendao.Usuario;
import com.dam.salesianostriana.calccalorias.greendao.UsuarioDao;


public class LoginActivity extends AppCompatActivity {
    Button btn_entrar;
    TextView txtNuevoUsuario, txtUsuario, txtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_entrar = (Button) findViewById(R.id.btn_entrar);
        txtNuevoUsuario = (TextView) findViewById(R.id.textViewNuevo);
        txtUsuario = (TextView) findViewById(R.id.textViewUs);
        txtPass = (TextView) findViewById(R.id.textViewContra);

        final UsuarioDao usuarioDao = Utils.instanciarBD(this).getUsuarioDao();
        SharedPreferences sharedPreferences = getSharedPreferences("preferencias",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        if(sharedPreferences.getString("id_usuario", null)!=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            LoginActivity.this.finish();
        }
        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String us = txtUsuario.getText().toString();
                String pas = txtPass.getText().toString();

                Usuario usuario = usuarioDao.queryBuilder().where(UsuarioDao.Properties.Usuario.eq(us)).where(UsuarioDao.Properties.Password.eq(pas)).unique();

                if(usuario == null){
                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }else{
                    editor.putString("id_usuario", String.valueOf(usuario.getId()));
                    editor.apply();

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    LoginActivity.this.finish();
                }

            }
        });

        txtNuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
                LoginActivity.this.finish();
            }

        });
    }
}
