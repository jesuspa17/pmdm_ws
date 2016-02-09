package com.dam.salesianostriana.ejercicio03_matapatov2.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesús Pallares on 08/02/2016.
 */
public class Usuario{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("points")
    @Expose
    private String points;

    /**
     * No args constructor for use in serialization
     *
     */
    public Usuario() {
    }

    /**
     *
     * @param id
     * @param nickname
     * @param points
     */
    public Usuario(String id, String nickname, String points) {
        this.id = id;
        this.nickname = nickname;
        this.points = points;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     *
     * @param nickname
     * The nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     *
     * @return
     * The points
     */
    public String getPoints() {
        return points;
    }

    /**
     *
     * @param points
     * The points
     */
    public void setPoints(String points) {
        this.points = points;
    }

}
