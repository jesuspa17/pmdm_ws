package com.dam.salesianostriana.calccalorias;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dam.salesianostriana.calccalorias.greendao.Ruta;
import com.dam.salesianostriana.calccalorias.greendao.RutaDao;
import com.db.chart.view.BarChartView;

import java.util.ArrayList;
import java.util.List;

public class GraficaFragment extends Fragment {

    private final static String[] mLabels = {" ", " ", " ", " ", " ", " ", " "};
    private final static float[] mValues = {0, 0, 0, 0, 0, 0, 0};
    public static BarChartView chart;
    public static RecyclerView recyclerView;
    public static RecyclerView.Adapter adapter;
    List<PojoRuta> lista;


    public GraficaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_grafica, container, false);

        //chart = (BarChartView) v.findViewById(R.id.stackBar);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        Log.i("ID_USUARIO", "ID " + sharedPreferences.getString("id_usuario", null));
        lista = new ArrayList<>();
        List<Ruta> listadao = Utils.instanciarBD(getActivity()).getRutaDao().queryBuilder().where(RutaDao.Properties.Id_usuario.eq(Long.parseLong(sharedPreferences.getString("id_usuario", null)))).orderAsc(RutaDao.Properties.Id).list();


       /* List<Ruta> listaFiltrada = Utils.instanciarBD(getActivity()).getRutaDao().queryBuilder().where(RutaDao.Properties.Id_usuario.eq(Long.parseLong(sharedPreferences.getString("id_usuario", null)))).orderDesc(RutaDao.Properties.Id).limit(7).list();

        if(listaFiltrada.size()!=0){

            String[] valores = new String[listaFiltrada.size()];
            float[] lista_cal = new float[listaFiltrada.size()];

            for(int i =0;i<listaFiltrada.size();i++){
                valores[i] = Utils.formatearFechaString(Utils.formatoFinal,listaFiltrada.get(i).getFecha().toString()).substring(0, 5);
                lista_cal[i] = Float.parseFloat(String.valueOf(listaFiltrada.get(i).getCalorias()));

            }

            BarSet dataset = new BarSet(valores, lista_cal);
            dataset.setColor(Color.parseColor("#D6E3F1"));
            float start = (float) 10;
            chart.setBarSpacing(start);
            chart.addData(dataset);
            chart.setAxisBorderValues(0, 1000, 200);

            chart.setLabelsFormat(new DecimalFormat('#' + " k/cal"));
            Animation anim = new Animation(500);
            anim.setEasing(new SineEase());
            anim.setAlpha(3);

            float start1 = (float) 1.0;
            anim.setStartPoint(start1, 0);
            chart.show(anim);

        }else{

            BarSet dataset = new BarSet(mLabels, mValues);
            dataset.setColor(Color.parseColor("#D6E3F1"));
            float start = (float) 10;
            chart.setBarSpacing(start);
            chart.addData(dataset);
            chart.setAxisBorderValues(0, 1000, 200);

            chart.setLabelsFormat(new DecimalFormat('#' + " k/cal"));
            Animation anim = new Animation(500);
            anim.setEasing(new SineEase());
            anim.setAlpha(3);

            float start1 = (float) 1.0;
            anim.setStartPoint(start1, 0);
            chart.show(anim);

        }*/


        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerRutas);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        for (int i = 0; i < listadao.size(); i++) {
            lista.add(new PojoRuta(Utils.formatearFechaString(Utils.formatoFinal, listadao.get(i).getFecha().toString()), listadao.get(i).getDistancia(), listadao.get(i).getCalorias(), Utils.getDiaSemana(),listadao.get(i).getDuracion()));
        }

        adapter = new RutaAdapter(lista);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        return v;
    }

}
