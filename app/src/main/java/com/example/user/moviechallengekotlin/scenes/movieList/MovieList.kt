package com.example.user.moviechallengekotlin.scenes.movieList

interface MovieList {

    interface View {
        fun displayMovies(movies: List<MovieListViewModel>, totalPages: Int?)
    }

    interface Presenter {
        fun getMovies(genreId: String, page: Int)
        fun getMovieByName(movieName: String)
    }
}