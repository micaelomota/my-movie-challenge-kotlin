package com.example.user.moviechallengekotlin.scenes.movieList

import com.example.user.moviechallengekotlin.db.FavoriteMovie

interface MovieList {

    interface View {
        fun displayMovies(movies: ArrayList<MovieListViewModel>, totalPages: Int?)
        fun displayFavoriteMovies(movies: ArrayList<MovieListViewModel>)
        fun displaySearchMovies(movies: ArrayList<MovieListViewModel>, totalPages: Int?)
        fun toggleFavoriteMovie(movie: MovieListViewModel)
        fun displayMovieDetails(movie: MovieListViewModel)
    }

    interface Presenter {
        fun getMovies()
        fun getMovieByName(movieName: String)
        fun toggleFavoriteMovie(movie: MovieListViewModel)
        fun getFavoriteMovies()
        fun refreshList()
    }
}