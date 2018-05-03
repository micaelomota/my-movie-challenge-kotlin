package com.example.user.moviechallengekotlin.scenes.movieList

import com.example.user.moviechallengekotlin.connection.RetrofitClient
import com.example.user.moviechallengekotlin.connection.movieService
import com.example.user.moviechallengekotlin.models.MovieList as MovieListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListPresenter(var view: MovieList.View): MovieList.Presenter {

    var call: Call<MovieListResponse>? = null

    override fun getMovies(genreId: String, page:Int) {
        call = RetrofitClient.instance?.movieService()?.getMoviesByGenre(genreId, page)

        call?.enqueue(object: Callback<com.example.user.moviechallengekotlin.models.MovieList> {
            override fun onResponse(call: Call<com.example.user.moviechallengekotlin.models.MovieList>?, response: Response<com.example.user.moviechallengekotlin.models.MovieList>?) {
                println("Filmes encontrados: ${response?.body()?.totalResults}")

                var movieList = arrayListOf<MovieListViewModel>()

                response?.body()?.results?.forEach {
                    if (it.title != null && it.posterPath != null) {
                        movieList.add(MovieListViewModel(it.title!!, it.posterPath!!, it.overview!!))
                    }
                }

                view.displayMovies(movieList as List<MovieListViewModel>, response?.body()?.totalPages)
            }

            override fun onFailure(call: Call<com.example.user.moviechallengekotlin.models.MovieList>?, t: Throwable?) {
                // ué
            }
        })
    }

    override fun getMovieByName(movieName: String) {
        call = RetrofitClient.instance?.movieService()?.searchMoviesByName(movieName)

        call?.enqueue(object: Callback<com.example.user.moviechallengekotlin.models.MovieList> {
            override fun onResponse(call: Call<com.example.user.moviechallengekotlin.models.MovieList>?, response: Response<com.example.user.moviechallengekotlin.models.MovieList>?) {
                println("Filmes encontrados: ${response?.body()?.totalResults}")

                var movieList = arrayListOf<MovieListViewModel>()

                response?.body()?.results?.forEach {
                    if (it.title != null && it.posterPath != null) {
                        movieList.add(MovieListViewModel(it.title!!, it.posterPath!!, it.overview!!))
                    }
                }

                view.displayMovies(movieList as List<MovieListViewModel>, response?.body()?.totalPages)
            }

            override fun onFailure(call: Call<com.example.user.moviechallengekotlin.models.MovieList>?, t: Throwable?) {
                // ué
            }
        })
    }
}