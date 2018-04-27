package com.example.user.moviechallengekotlin.scenes.movieList

interface MovieList {

    interface View {
        fun displayMovies(movies: List<MovieListViewModel>)
    }

    interface Presenter {

        fun getMovies()

    }
}