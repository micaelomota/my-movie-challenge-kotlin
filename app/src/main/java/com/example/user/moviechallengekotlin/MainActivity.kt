package com.example.user.moviechallengekotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.user.moviechallengekotlin.api.RetrofitClient
import com.example.user.moviechallengekotlin.api.movieService
import com.example.user.moviechallengekotlin.pojo.movielist.MovieList
import com.example.user.moviechallengekotlin.pojo.movielist.Result
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var listView: RecyclerView
    lateinit var adapter: RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>
    val movieList = arrayListOf<Result>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.title_movies)

        listView = moviesRV
        listView.layoutManager = GridLayoutManager(this, 2)

        adapter = MovieListAdapter(movieList, this)
        listView.adapter = adapter

        val call = RetrofitClient.instance?.movieService()?.getNowPlaying()

        call?.enqueue(object: Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>?, response: Response<MovieList>?) {
                println("Filmes encontrados: ${response?.body()?.totalResults}")

                movieList.clear()
                movieList.addAll(response?.body()?.results as List<Result>)

                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<MovieList>?, t: Throwable?) {
                // ué
            }
        })
    }

    fun seeMovieDetails(title: String?, overview: String?, posterPath: String?) {
        val i = Intent(this, MovieDetailsActivity::class.java)
        i.putExtra(MovieDetailsActivity.MOVIE_TITLE, title)
        i.putExtra(MovieDetailsActivity.MOVIE_OVERVIEW, overview)
        i.putExtra(MovieDetailsActivity.MOVIE_POSTER_PATH, posterPath)
        this.startActivity(i)
    }
}
