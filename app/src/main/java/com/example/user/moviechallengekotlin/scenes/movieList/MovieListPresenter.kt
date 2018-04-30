package com.example.user.moviechallengekotlin.scenes.movieList

import com.example.user.moviechallengekotlin.connection.RetrofitClient
import com.example.user.moviechallengekotlin.connection.movieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListPresenter(var view: MovieList.View): MovieList.Presenter {

    override fun getMovies(genreId: String) {
        val call = RetrofitClient.instance?.movieService()?.getMoviesByGenre(genreId)

        call?.enqueue(object: Callback<com.example.user.moviechallengekotlin.models.MovieList> {
            override fun onResponse(call: Call<com.example.user.moviechallengekotlin.models.MovieList>?, response: Response<com.example.user.moviechallengekotlin.models.MovieList>?) {
                println("Filmes encontrados: ${response?.body()?.totalResults}")

                var movieList = arrayListOf<MovieListViewModel>();

                response?.body()?.results?.forEach {
                    if (it.title != null && it.posterPath != null) {
                        movieList.add(MovieListViewModel(it.title!!, it.posterPath!!, it.overview!!))
                    }
                }

                view.displayMovies(movieList as List<MovieListViewModel>)
            }

            override fun onFailure(call: Call<com.example.user.moviechallengekotlin.models.MovieList>?, t: Throwable?) {
                // ué
            }
        })

    }


}