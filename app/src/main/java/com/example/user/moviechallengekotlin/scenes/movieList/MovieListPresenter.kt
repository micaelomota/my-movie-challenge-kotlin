package com.example.user.moviechallengekotlin.scenes.movieList

import android.content.Context
import com.example.user.moviechallengekotlin.connection.RetrofitClient
import com.example.user.moviechallengekotlin.connection.movieService
import com.example.user.moviechallengekotlin.db.FavoriteMovie
import com.example.user.moviechallengekotlin.db.FavoriteMovieDataBase
import com.example.user.moviechallengekotlin.models.MovieList as MovieListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListPresenter(private var view: MovieList.View, private val context: Context): MovieList.Presenter {

    private var call: Call<MovieListResponse>? = null

    override fun getMovies(genreId: String, page:Int) {
        call = RetrofitClient.instance?.movieService()?.getMoviesByGenre(genreId, page)

        call?.enqueue(object: Callback<com.example.user.moviechallengekotlin.models.MovieList> {
            override fun onResponse(call: Call<com.example.user.moviechallengekotlin.models.MovieList>?, response: Response<com.example.user.moviechallengekotlin.models.MovieList>?) {
                println("Filmes encontrados: ${response?.body()?.totalResults}")

                val movieList = arrayListOf<MovieListViewModel>()

                response?.body()?.results?.forEach {
                    if (it.title != null && it.posterPath != null) {
                        movieList.add(MovieListViewModel(it.id!!, it.title!!, it.posterPath!!, it.overview!!, getFavoriteMovie(it.id!!) != null))
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

                val movieList = arrayListOf<MovieListViewModel>()

                response?.body()?.results?.forEach {
                    if (it.title != null && it.posterPath != null) {
                        movieList.add(MovieListViewModel(it.id!!, it.title!!, it.posterPath!!, it.overview!!, getFavoriteMovie(it.id!!) != null))
                    }
                }

                view.displaySearchMovies(movieList, response?.body()?.totalPages)
            }

            override fun onFailure(call: Call<com.example.user.moviechallengekotlin.models.MovieList>?, t: Throwable?) {
                // ué
            }
        })
    }

    override fun setFavoriteMovie(movie: MovieListViewModel) {
        val mDb = FavoriteMovieDataBase.getInstance(context)
        mDb.favoriteMovieDao().insert(FavoriteMovie(movie.id, movie.title, movie.overview, movie.posterPath))
    }

    override fun unsetFavoriteMovie(id: Int) {
        val mDb = FavoriteMovieDataBase.getInstance(context)
        mDb.favoriteMovieDao().delete(id)
    }

    override fun getFavoriteMovie(id: Int): FavoriteMovie? {
        val mDb = FavoriteMovieDataBase.getInstance(context)
        return mDb.favoriteMovieDao().getMovieById(id)
    }

    override fun getAllFavoriteMovies(): List<MovieListViewModel> {
        val mDb = FavoriteMovieDataBase.getInstance(context)
        val favoriteMovieList = arrayListOf<MovieListViewModel>()
        mDb.favoriteMovieDao().getAll().map {
            favoriteMovieList.add(MovieListViewModel(it.id, it.title,  it.posterPath, it.overview,true))
        }
        return favoriteMovieList
    }
}