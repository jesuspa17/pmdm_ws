package com.dam.salesianostriana.calccalorias;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Jesus Pallares on 23/01/2016.
 */
public class RutaAdapter extends RecyclerView.Adapter<RutaAdapter.ViewHolder> {

    private List<PojoRuta> lista_datos;
    Context contexto;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView num_ruta,distancia,calorias,dia_semana, duracion;
        View mi_vista;


        public ViewHolder(View v) {
            super(v);

            num_ruta = (TextView) v.findViewById(R.id.textViewFechaRuta);
            distancia = (TextView) v.findViewById(R.id.textViewDistanciaAd);
            calorias = (TextView) v.findViewById(R.id.textViewCaloriasAd);
            dia_semana = (TextView) v.findViewById(R.id.textViewDiaSemana);
            duracion = (TextView) v.findViewById(R.id.textViewDuracion);

            mi_vista = v;
        }
    }

    public RutaAdapter(List<PojoRuta> lista_datos) {
        this.lista_datos = lista_datos;
    }

    @Override
    public RutaAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_rutas, viewGroup, false);

        contexto = v.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RutaAdapter.ViewHolder holder, int i) {

        //Obtiene el sitio de la iteración.
        final PojoRuta ruta_actual = lista_datos.get(i);

        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        holder.num_ruta.setText(String.valueOf(ruta_actual.getFecha()));
        holder.distancia.setText(String.valueOf(decimalFormat.format(ruta_actual.getDistancia())));
        holder.calorias.setText(String.valueOf(decimalFormat.format(ruta_actual.getCalorias())));
        holder.dia_semana.setText(ruta_actual.getDia_semana());
        holder.duracion.setText(ruta_actual.getDuracion());

    }

    @Override
    public int getItemCount() {
        return lista_datos.size();
    }


}
