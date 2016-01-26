package com.dam.salesianostriana.calccalorias;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.salesianostriana.calccalorias.greendao.Usuario;
import com.dam.salesianostriana.calccalorias.greendao.UsuarioDao;

public class PerfilActivity extends AppCompatActivity {

    EditText nombre,peso,usuario,password;
    Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        nombre = (EditText) findViewById(R.id.editTextNombreEditar);
        peso = (EditText) findViewById(R.id.editTextPesoEditar);
        usuario = (EditText) findViewById(R.id.editTextUsuarioEditar);
        password = (EditText) findViewById(R.id.editTextPassEditar);
        guardar = (Button) findViewById(R.id.button_guardarCambios);

        SharedPreferences prefs = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        final long id_usuario = Long.parseLong(prefs.getString("id_usuario", null));

        final UsuarioDao usuarioDao = Utils.instanciarBD(PerfilActivity.this).getUsuarioDao();
        final Usuario usuarioNuevo = usuarioDao.queryBuilder().where(UsuarioDao.Properties.Id.eq(id_usuario)).unique();

        nombre.setText(usuarioNuevo.getNombre());
        peso.setText(String.valueOf(usuarioNuevo.getPeso()));
        usuario.setText(usuarioNuevo.getUsuario());
        password.setText(usuarioNuevo.getPassword());


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nombre.getText().toString().isEmpty()||peso.getText().toString().isEmpty()||usuario.getText().toString().isEmpty()||password.getText().toString().isEmpty()){
                    Toast.makeText(PerfilActivity.this,"Revise que no haya ningún campo vacío.",Toast.LENGTH_SHORT).show();
                }else{


                    usuarioNuevo.setNombre(nombre.getText().toString());
                    usuarioNuevo.setPeso(Double.parseDouble(peso.getText().toString()));
                    usuarioNuevo.setUsuario(usuario.getText().toString());
                    usuarioNuevo.setPassword(password.getText().toString());

                    usuarioDao.update(usuarioNuevo);

                    PerfilActivity.this.startActivity(new Intent(PerfilActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                }


            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PerfilActivity.this.finish();
    }
}
