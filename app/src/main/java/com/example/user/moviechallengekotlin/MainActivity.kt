package com.example.user.moviechallengekotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.user.moviechallengekotlin.api.RetrofitClient
import com.example.user.moviechallengekotlin.api.movieService
import com.example.user.moviechallengekotlin.pojo.movielist.MovieList
import com.example.user.moviechallengekotlin.pojo.movielist.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var listView: RecyclerView
    lateinit var adapter: RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>
    val movieList = arrayListOf<Result>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById<RecyclerView>(R.id.rv_movies)
        listView.layoutManager = GridLayoutManager(this, 2)

        adapter = MovieListAdapter(movieList, this)
        listView.adapter = adapter


        var call = RetrofitClient.instance?.movieService()?.getNowPlaying()

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
}
