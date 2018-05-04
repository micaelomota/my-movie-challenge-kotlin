package com.example.user.moviechallengekotlin.scenes.movieList

data class MovieListViewModel(val id: Int, val title: String, val posterPath: String, val overview: String, var isFavorite: Boolean)