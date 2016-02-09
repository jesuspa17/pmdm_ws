package com.dam.salesianostriana.ejercicio03_matapatov2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dam.salesianostriana.ejercicio03_matapatov2.pojos.Usuario;

import java.util.ArrayList;

/**
 * Created by Jesús Pallares on 08/02/2016.
 */
public class RankingAdapter extends ArrayAdapter<Usuario> {

    Context context;
    ArrayList<Usuario> lista;
    SharedPreferences preferences;

    public RankingAdapter(Context context, ArrayList<Usuario> objects) {
        super(context, 0, objects);
        this.context = context;
        this.lista = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = LayoutInflater.from(context).inflate(R.layout.ranking_item, parent, false);
        preferences = context.getSharedPreferences("kill_pato", Context.MODE_PRIVATE);
        String name = preferences.getString("nick", null);
        Log.i("NICKNAME",name);
        Usuario user = lista.get(position);
        Log.i("POSICION",String.valueOf(position));



        LinearLayout contenido = (LinearLayout) v.findViewById(R.id.linealContenido);
        TextView posicion = (TextView) v.findViewById(R.id.textViewPosition);
        TextView nick = (TextView) v.findViewById(R.id.textViewNickUser);
        TextView puntuacion = (TextView) v.findViewById(R.id.textViewPuntuacionLista);

        if(position == 0){

            contenido.setBackgroundColor(Color.parseColor("#F8D146"));

        }else if(position == 1){

            contenido.setBackgroundColor(Color.parseColor("#92929C"));
        }else if(position == 2){

            contenido.setBackgroundColor(Color.parseColor("#C88045"));
        }

        posicion.setText(String.valueOf(position + 1));
        nick.setText(user.getNickname());
        puntuacion.setText(user.getPoints());


        return v;
    }
}
