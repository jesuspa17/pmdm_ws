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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class GraficaFragment extends Fragment {

    public static RecyclerView recyclerView;
    public static RecyclerView.Adapter adapter;
    List<PojoRuta> lista;

    Float l,m,x,j,v,s,d;
    String a,b,c,dm,e,f,g;


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


        BarChart chart = (BarChart) v.findViewById(R.id.chart);

        //m=Float.valueOf(a);
        // m=123.000f;
        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("Estadísticas");
        chart.animateXY(2000, 2000);
        chart.invalidate();


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

    public ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;
        BarDataSet barDataSet2 = null;
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("preferencias", Context.MODE_PRIVATE);

        List<Ruta> listaFiltrada = Utils.instanciarBD(getActivity()).getRutaDao().queryBuilder().where(RutaDao.Properties.Id_usuario.eq(Long.parseLong(sharedPreferences.getString("id_usuario", null)))).orderAsc(RutaDao.Properties.Id).limit(7).list();
        ArrayList<BarEntry> valueSet2 = new ArrayList<>();

        for(int i = 0;i<listaFiltrada.size();i++){
            BarEntry v2e1 = new BarEntry(Float.parseFloat(String.valueOf(listaFiltrada.get(i).getCalorias())), i);
            valueSet2.add(v2e1);
            barDataSet2 = new BarDataSet(valueSet2, "Día: "+(i+1));
            barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
        }

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet2);

        return dataSets;
    }

    public ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("L");
        xAxis.add("M");
        xAxis.add("X");
        xAxis.add("J");
        xAxis.add("V");
        xAxis.add("S");
        xAxis.add("D");
        return xAxis;
    }

}
