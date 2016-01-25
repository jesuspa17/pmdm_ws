package com.dam.salesianostriana.calccalorias;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dam.salesianostriana.calccalorias.greendao.DaoMaster;
import com.dam.salesianostriana.calccalorias.greendao.DaoSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Jesus Pallares on 21/01/2016.
 */
public class Utils {

    public static DaoSession instanciarBD(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "RunTrianasticDB", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        return daoSession;
    }


    public static String FORMATO_FECHA = "yyyy-MM-dd'T'HH:mm:ss";
    public static String formatoFinal = "EEE MMM dd HH:mm:ss z yyyy";

    public static String formatearFechaString(String formato, String fecha) {
        //formato de entrada
        SimpleDateFormat f = new SimpleDateFormat(formato);
        //formato de salida
        SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");
        String fecha_formateada = "";
        try {
            Date date = f.parse(fecha);
            fecha_formateada = f1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fecha_formateada;
    }

    public static String getDiaSemana() {
        String valor_dia = null;

        GregorianCalendar fechaCalendario = new GregorianCalendar();
        fechaCalendario.setTime(new Date());
        int diaSemana = fechaCalendario.get(Calendar.DAY_OF_WEEK);

        if (diaSemana == 1) {
            valor_dia = "Domingo";
        } else if (diaSemana == 2) {
            valor_dia = "Lunes";
        } else if (diaSemana == 3) {
            valor_dia = "Martes";
        } else if (diaSemana == 4) {
            valor_dia = "Miércoles";
        } else if (diaSemana == 5) {
            valor_dia = "Jueves";
        } else if (diaSemana == 6) {
            valor_dia = "Viernes";
        } else if (diaSemana == 7) {
            valor_dia = "Sábado";
        }
        return valor_dia;
    }


    public static String calcularTiempo(String inicio, String fecha_fi){

        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(inicio);
            d2 = format.parse(fecha_fi);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert d2 != null;
        long diff = d2.getTime() - d1.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        Log.i("DIFERENCIA", "DIFERENCIA: " + diffHours + "h:" + diffMinutes + "m:" + diffSeconds + "s");

        return diffHours+"h:"+diffMinutes+"m:"+diffSeconds+"s";
    }
}
