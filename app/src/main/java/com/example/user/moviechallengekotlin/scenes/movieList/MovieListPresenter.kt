package com.example.user.moviechallengekotlin.scenes.movieList

import com.example.user.moviechallengekotlin.connection.RetrofitClient
import com.example.user.moviechallengekotlin.connection.movieService
import com.example.user.moviechallengekotlin.db.FavoriteMovie
import com.example.user.moviechallengekotlin.db.FavoriteMovieDataBase
import com.example.user.moviechallengekotlin.models.MovieList as MovieListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListPresenter(private var view: MovieList.View,
                         private val genreId: String,
                         private val mDb: FavoriteMovieDataBase): MovieList.Presenter {

    private var call: Call<MovieListResponse>? = null
    private var currentPage = 1
    var movieList: ArrayList<MovieListViewModel> = arrayListOf()
    var searchMovieList: ArrayList<MovieListViewModel> = arrayListOf()


    override fun getMovies() {
        call = RetrofitClient.instance?.movieService()?.getMoviesByGenre(genreId, currentPage++)

        call?.enqueue(object: Callback<com.example.user.moviechallengekotlin.models.MovieList> {
            override fun onResponse(call: Call<com.example.user.moviechallengekotlin.models.MovieList>?, response: Response<com.example.user.moviechallengekotlin.models.MovieList>?) {
                println("Filmes encontrados: ${response?.body()?.totalResults}")

                val movieList = arrayListOf<MovieListViewModel>()
                response?.body()?.results?.forEach {
                    if (it.title != null && it.posterPath != null) {
                        movieList.add(MovieListViewModel(it.id!!, it.title!!, it.posterPath!!, it.overview!!, getFavoriteMovie(it.id!!) != null))
                    }
                }
                view.displayMovies(movieList, currentPage == response?.body()?.totalPages)
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

                val movieList = arrayListOf<MovieListViewModel>()

                response?.body()?.results?.forEach {
                    if (it.title != null && it.posterPath != null) {
                        movieList.add(MovieListViewModel(it.id!!, it.title!!, it.posterPath!!, it.overview!!, getFavoriteMovie(it.id!!) != null))
                    }
                }

                view.displaySearchMovies(movieList, currentPage == response?.body()?.totalPages)
            }

            override fun onFailure(call: Call<com.example.user.moviechallengekotlin.models.MovieList>?, t: Throwable?) {
                // ué
            }
        })
    }

    override fun toggleFavoriteMovie(movie: MovieListViewModel) {
        if (getFavoriteMovie(movie.id) != null ) {
            mDb.favoriteMovieDao().delete(movie.id)
        } else {
            mDb.favoriteMovieDao().insert(FavoriteMovie(movie.id, movie.title, movie.overview, movie.posterPath))
        }
    }

    fun getFavoriteMovie(id: Int): FavoriteMovie? {
        return mDb.favoriteMovieDao().getMovieById(id)
    }

    override fun getFavoriteMovies() {
        movieList = ArrayList(mDb.favoriteMovieDao().getAll().map {
            MovieListViewModel(it.id, it.title,  it.posterPath, it.overview,true)
        })

        view.displayFavoriteMovies(movieList)
    }

    override fun refreshList() {
        currentPage = 1
        movieList.clear()
        getMovies()
    }
}