package com.example.user.moviechallengekotlin.connection.service;

import com.example.user.moviechallengekotlin.models.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path

interface MovieService {
    @GET("movie/now_playing?")
    fun getNowPlaying(): Call<MovieList>

    @GET("genre/{genre_id}/movies")
    fun getMoviesByGenre(@Path("genre_id") genreID: String): Call<MovieList>
}
