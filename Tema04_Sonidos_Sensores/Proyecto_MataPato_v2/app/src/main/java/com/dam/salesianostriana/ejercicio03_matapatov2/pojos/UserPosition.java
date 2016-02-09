package com.dam.salesianostriana.ejercicio03_matapatov2.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesus Pallares on 08/02/2016.
 */
public class UserPosition {
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("points")
    @Expose
    private String points;

    /**
     * @return The nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname The nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return The position
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @param position The position
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * @return The points
     */
    public String getPoints() {
        return points;
    }

    /**
     * @param points The points
     */
    public void setPoints(String points) {
        this.points = points;
    }

}