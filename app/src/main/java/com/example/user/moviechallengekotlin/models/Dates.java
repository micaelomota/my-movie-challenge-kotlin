package com.example.user.moviechallengekotlin.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dates {

    @SerializedName("maximum")
    @Expose
    public String maximum;
    @SerializedName("minimum")
    @Expose
    public String minimum;

}