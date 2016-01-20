package com.dam.salesianostriana.calccalorias;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.chart.model.Bar;
import com.db.chart.model.BarSet;
import com.db.chart.view.BarChartView;

public class GraficaFragment extends Fragment {

    private final static String[] mLabels= {"W", "I", "L", "L", "I", "A", "M"};
    private final static float[] mValues = {2.5f, 3.7f, 4f, 8f, 4.5f, 4f, 5f};


    public GraficaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    BarChartView barChartView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_grafica, container, false);

        barChartView = (BarChartView) v.findViewById(R.id.barchart);
        BarSet barSet = new BarSet();

        barSet.addBar(new Bar("hola",354));
        barSet.addBar(new Bar("juan",50));
        barSet.addBar(new Bar("pepe",100));



        return v;
    }

}
