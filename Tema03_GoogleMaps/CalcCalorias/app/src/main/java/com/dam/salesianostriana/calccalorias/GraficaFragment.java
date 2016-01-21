package com.dam.salesianostriana.calccalorias;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.chart.model.BarSet;
import com.db.chart.view.BarChartView;
import com.db.chart.view.animation.Animation;
import com.db.chart.view.animation.easing.SineEase;

import java.text.DecimalFormat;

public class GraficaFragment extends Fragment {

    private final static String[] mLabels= {"LUN", "MAR", "MIER", "JUE", "VIE", "SAB", "DOM"};
    private final static float[] mValues = {200,300, 400, 500, 200, 200, 700};


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

        BarChartView chart = (BarChartView) v.findViewById(R.id.stackBar);

        // Stacked chart customization
        BarSet dataset = new BarSet(mLabels, mValues);
        dataset.setColor(Color.parseColor("#D6E3F1"));
        float start= (float) 10;
        chart.setBarSpacing(start);
        chart.addData(dataset);
         chart.setAxisBorderValues(0,1000,200);


        // Generic chart customization
        chart.setLabelsFormat(new DecimalFormat('#' + " k/cal"));

        // Animation customization
        Animation anim = new Animation(500);
        anim.setEasing(new SineEase());
        anim.setAlpha(3);

        float start1= (float) 1.0;
        anim.setStartPoint(start1, 0);
        chart.show(anim);

        return v;
    }

}
