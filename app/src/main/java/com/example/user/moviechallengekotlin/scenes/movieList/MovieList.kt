package com.example.user.moviechallengekotlin.scenes.movieList

import com.example.user.moviechallengekotlin.db.FavoriteMovie

interface MovieList {

    interface View {
        fun displayMovies(movies: List<MovieListViewModel>, totalPages: Int?)
        fun toggleFavoriteMovie(movie: MovieListViewModel)
        fun displayMovieDetails(movie: MovieListViewModel)
    }

    interface Presenter {
        fun getMovies(genreId: String, page: Int)
        fun getMovieByName(movieName: String)
        fun setFavoriteMovie(movie: MovieListViewModel)
        fun unsetFavoriteMovie(id: Int)
        fun getFavoriteMovie(id: Int): FavoriteMovie?
        fun getAllFavoriteMovies(): List<MovieListViewModel>
    }
}