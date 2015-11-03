package com.dam.salesianostriana.pmdm.reproductormusica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jesus on 31/10/2015.
 */
public class CancionAdapter extends ArrayAdapter<Cancion> {
    private final Context context;
    private final ArrayList<Cancion> values;

    public CancionAdapter(Context context, ArrayList<Cancion> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Comentario Miguel:
        // La siguiente línea de código recibe como parámetro el layout
        // que he diseñado para un elemento del ListView, en este caso
        // para un Alumno de la lista >>> R.layout.list_item_alumno

        View layoutCanciones = inflater.inflate(R.layout.list_item_cancion, parent, false);
        TextView grupo = (TextView) layoutCanciones.findViewById(R.id.textViewGrupo);
        TextView cancion = (TextView) layoutCanciones.findViewById(R.id.textViewCancion);
        TextView duracion = (TextView) layoutCanciones.findViewById(R.id.textViewDuracion);

        // Se busca en el layout los elementos que vamos a utilizar.

        //Comentario Miguel:
        // Para poder llenar los elementos del Layout de contenido, necesito obtener
        // los datos del alumno que estoy recorriendo en esta iteración

        Cancion cancionActual = values.get(position);

        grupo.setText(cancionActual.getGrupo());
        cancion.setText(cancionActual.getTitutlo());
        duracion.setText(cancionActual.getDuracion());


        return layoutCanciones;
    }
}
