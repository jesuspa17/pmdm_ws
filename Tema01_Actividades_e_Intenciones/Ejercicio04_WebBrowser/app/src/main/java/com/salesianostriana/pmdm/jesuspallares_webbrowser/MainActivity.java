package com.salesianostriana.pmdm.jesuspallares_webbrowser;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 Proyecto Web Browser
 @author Jesús Pallares
 Curso 2ºDAM

Con este proyecto pretendemos diseñar la pantalla de un Navegador, que debe constar de:

    Barra de direcciones
    Botón de buscar
    Botón de favoritos
    Visor de página web

1. CARGAR PÁGINAS WEBSEl funcionamiento consistirá en que cada vez que una persona escriba una URL en 
barra de direcciones y pulse en el botón buscar, deberá cargarse dicha URL en el visor de página web.

2. APARECER COMO APP DE NAVEGACIÓN

    Además, la aplicación debe poder lanzarse desde otra aplicación que realice un Intent implícito, 
    como el siguiente:
    
    Uri webpage = Uri.parse("http://www.android.com");
    Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
    
    Para poder capturar esta acción debemos implementar un Intent-filter en nuestra aplicación con 
    la siguiente información:
    
    <action android:name="android.intent.action.VIEW" />
    <data android:scheme="http" />
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />.

3. FAVORITOS
    La aplicación deberá permitir guardas páginas en favorito.
 */
public class MainActivity extends AppCompatActivity {
    //Elementos obtenidos de la UI
    @Bind(R.id.editDireccion)
    EditText url;

    @Bind(R.id.webView)
    WebView web;

    @Bind(R.id.btn_buscar)
    ImageButton buscar;

    @Bind(R.id.btn_fav)
    ImageButton btn_fav;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.btn_lista)
    ImageButton btn_lista;

    ArrayList<String> misFavoritos;

    @BindDrawable(R.drawable.favon)
    Drawable favon;

    @BindDrawable(R.drawable.favoff)
    Drawable favoff;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //carga página de inicio
        url.setText("google.es");
        web.loadUrl("http://"+url.getText().toString().trim().toLowerCase());

        Intent i= getIntent();
        String direccion= i.getDataString();

        if(direccion == null){

        }else{
            url.setText(direccion);
            web.loadUrl(direccion);
        }

        //inicializamos el array donde se almacenarán los favoritos
        misFavoritos = new ArrayList<String>();

        //se habilita javascript para la carga de las páginas y zoom que permitirá
        //acercar y alejar la página.
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setBuiltInZoomControls(true);

        //Carga la url de inicio



        //evita que los enlaces se abran fuera nuestra app
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

        });

        //BARRA DE PROCESOS
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
                MainActivity.this.setProgress(progress * 1000);
                progressBar.incrementProgressBy(progress);
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

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

    /**
     * Busca en el WebView según los que tenga en la barra de direcciones
     * @param v
     */
    @OnClick(R.id.btn_buscar)
    public void search(View v) {
        //si el array esta vacío, busca lo que tengamos puesto en la barra de direcciones
        if(misFavoritos.isEmpty()){
            url.setText(url.getText().toString().trim().toLowerCase());
            web.loadUrl("http://" + url.getText().toString().toLowerCase());
        //si hay algún elemento en el array, empieza a comparar lo escrito en la barra de direcciones
            //con los elementos que ya tenga para comprobar si está marcado como favorito o no.
        }else {
            for (int i = 0; i < misFavoritos.size(); i++) {
                //si está en la lita de favoritos:
                if (url.getText().toString().trim().contains(misFavoritos.get(i))) {
                    btn_fav.setImageDrawable(favon);
                    url.setText(url.getText().toString().toLowerCase());
                    web.loadUrl("http://" + url.getText().toString().toLowerCase());
                    break;
                //si no está:
                } else {
                    btn_fav.setImageDrawable(favoff);
                    url.setText(url.getText().toString().toLowerCase());
                    web.loadUrl("http://" + url.getText().toString().toLowerCase());
                }

            }
        }

    }

    /**
     * Abre el Activity donde se encuentran almacenados
     * los favoritos que vayamos añadiendo
     * @param v
     */
    @OnClick(R.id.btn_lista)
    public void abrirLista(View v) {
        Intent i = new Intent(this, FavoritosActivity.class);
        //"arrayFav" es la id con la que obtendremos los datos en el otro activity.
        i.putExtra("arrayfav", misFavoritos);
        startActivity(i);
    }

    /**
     * Añade o borra elementos del array donde se almacenan las páginas favoritas.
     * @param v
     */
    @OnClick(R.id.btn_fav)
    public void anyadirFav(ImageButton v) {

        //comprueba que si está vacío, añada un elemento
        if (misFavoritos.isEmpty()) {
            v.setImageDrawable(favon);
            misFavoritos.add(url.getText().toString().trim());
            Toast.makeText(this,"Añadido a favoritos correctamente", Toast.LENGTH_LONG).show();

        } else{
            for (int i = 0; i < misFavoritos.size(); i++) {

                //si el elemento obtenido de la barra de direcciones no existe en el array, lo añade a la lista de favoritos
                //y coloca el botón en "favon" (estrella color amarillo) para que se sepa que está marcado como favorito.
                if (!misFavoritos.get(i).equalsIgnoreCase(url.getText().toString().trim()) && v.getDrawable().equals(favoff)) {
                    v.setImageDrawable(favon);
                    misFavoritos.add(url.getText().toString().trim());
                    Toast.makeText(this,"Añadido a favoritos correctamente", Toast.LENGTH_LONG).show();
                    break;

                 //si está como favorito y volvemos a pulsar sobre el botón, borrará de la lista ese elemento.
                }else if(misFavoritos.get(i).equalsIgnoreCase(url.getText().toString().trim()) && v.getDrawable().equals(favon)){
                    v.setImageDrawable(favoff);
                    misFavoritos.remove(i);
                    Toast.makeText(this,"Borado de favoritos correctamente", Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
    }

}


