package com.example.user.moviechallengekotlin.connection.service;

import com.example.user.moviechallengekotlin.models.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("genre/{genre_id}/movies")
    fun getMoviesByGenre(@Path("genre_id") genreID: String, @Query("page") page: Int): Call<MovieList>

    @GET("search/movie?")
    fun searchMoviesByName(@Query("query") query: String): Call<MovieList>
}
