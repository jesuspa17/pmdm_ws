package com.dam.salesianostriana.ejercicio03_matapatov2;

import com.dam.salesianostriana.ejercicio03_matapatov2.pojos.UserPosition;
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
    Call<ArrayList<String>> registrarse(@Query("nickname") String object_id);

    @GET("/killduck/ranking")
    Call<ArrayList<Usuario>> obtenerRanking();

    @GET("/killduck/me?nickname=")
    Call<UserPosition> obtenerPosicionActual(@Query("nickname") String nick);

    @GET("/killduck/user?nickname")
    Call<Usuario> almacenarPuntuacion(@Query("nickname")String nick, @Query("points") String points);

}
