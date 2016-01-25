package com.dam.salesianostriana.calccalorias;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.salesianostriana.calccalorias.greendao.Usuario;
import com.dam.salesianostriana.calccalorias.greendao.UsuarioDao;


public class RegistroActivity extends AppCompatActivity {
    Button btn_registro;
    EditText nombre, peso, usuario, contrasenya;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_registro = (Button) findViewById(R.id.btn_registrarse);
        nombre = (EditText) findViewById(R.id.editTextNombre);
        peso = (EditText) findViewById(R.id.editTextPeso);
        usuario = (EditText) findViewById(R.id.editTextUsuario);
        contrasenya = (EditText)findViewById(R.id.editTextPass);

        final UsuarioDao usuarioDao = Utils.instanciarBD(this).getUsuarioDao();

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = nombre.getText().toString();
                String pes = peso.getText().toString();
                String us = usuario.getText().toString();
                String pass = contrasenya.getText().toString();

                if(nom.isEmpty()||pes.isEmpty()||us.isEmpty()||pass.isEmpty()){
                    Toast.makeText(RegistroActivity.this,"Debe rellenar los campos", Toast.LENGTH_SHORT).show();
                }else{
                    Usuario user = usuarioDao.queryBuilder().where(UsuarioDao.Properties.Usuario.eq(us)).unique();
                    if(user == null){
                        usuarioDao.insert(new Usuario(nom, Double.parseDouble(pes), us, pass));
                        startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                        RegistroActivity.this.finish();
                        Toast.makeText(RegistroActivity.this,"Se ha dado de alta correctamente, ahora puede loguearse", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(RegistroActivity.this,"Ya existe un usuario dado de alta con ese nombre de usuario, por favor, cámbielo", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

    }

}
