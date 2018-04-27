package com.example.user.moviechallengekotlin.connection.service;

import com.example.user.moviechallengekotlin.models.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;

interface MovieService {
    @GET("movie/now_playing?")
    fun getNowPlaying(): Call<MovieList>
}
