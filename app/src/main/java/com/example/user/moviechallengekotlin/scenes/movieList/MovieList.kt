package com.example.user.moviechallengekotlin.scenes.movieList

import com.example.user.moviechallengekotlin.db.FavoriteMovie

interface MovieList {

    interface View {
        fun displayMovies(movies: ArrayList<MovieListViewModel>, isLastPage: Boolean)
        fun displayFavoriteMovies(movies: ArrayList<MovieListViewModel>)
        fun displaySearchMovies(movies: ArrayList<MovieListViewModel>, isLastPage: Boolean)
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