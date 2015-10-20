package com.salesianostriana.di.intentsmultiples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailActivity extends AppCompatActivity {
    @Bind(R.id.btn_enviar)
    Button enviar;
    @Bind(R.id.editDireccion)
    EditText direccion;
    @Bind(R.id.editAsunto)
    EditText asunto;
    @Bind(R.id.editTexto)
    EditText texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_email, menu);
        ButterKnife.bind(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_enviar)
    public void enviarEmail(View v){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {direccion.getText().toString()}); // recipients
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, texto.getText().toString());

        startActivity(emailIntent);


    }
}
