package com.example.user.moviechallengekotlin.api.service;

import com.example.user.moviechallengekotlin.pojo.movielist.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;

interface MovieService {
    @GET("movie/now_playing?")
    fun getNowPlaying(): Call<MovieList>
}
