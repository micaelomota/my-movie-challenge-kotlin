package com.example.user.moviechallengekotlin.connection.service;

import com.example.user.moviechallengekotlin.models.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/now_playing?")
    fun getNowPlaying(): Call<MovieList>

    @GET("genre/{genre_id}/movies")
    fun getMoviesByGenre(@Path("genre_id") genreID: String): Call<MovieList>

    @GET("search/movie?")
    fun searchMoviesByName(@Query("query") query: String): Call<MovieList>
}
