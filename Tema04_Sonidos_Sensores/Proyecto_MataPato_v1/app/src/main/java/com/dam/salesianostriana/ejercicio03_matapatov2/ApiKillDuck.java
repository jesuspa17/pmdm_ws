package com.dam.salesianostriana.ejercicio03_matapatov2;

import com.dam.salesianostriana.ejercicio03_matapatov2.pojos.Usuario;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Jesús Pallares on 04/02/2016.
 */
public interface ApiKillDuck {

    @GET("/killduck/register?nickname=")
    Call<String> registrarse(@Query("nickname") String object_id);

    @GET("/killduck/ranking")
    Call<ArrayList<Usuario>> obtenerRanking();


}
