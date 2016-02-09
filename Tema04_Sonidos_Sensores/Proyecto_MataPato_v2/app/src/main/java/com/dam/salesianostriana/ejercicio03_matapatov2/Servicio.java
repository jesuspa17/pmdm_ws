package com.dam.salesianostriana.ejercicio03_matapatov2;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Jesús Pallares on 04/02/2016.
 */
public class Servicio {

    public static ApiKillDuck instanciarServicio() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://rest.miguelcr.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiKillDuck service = retrofit.create(ApiKillDuck.class);

        return service;
    }
}
