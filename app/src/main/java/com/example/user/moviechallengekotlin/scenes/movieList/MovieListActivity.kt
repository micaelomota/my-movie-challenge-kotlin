package com.example.user.moviechallengekotlin.scenes.movieList

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.user.moviechallengekotlin.scenes.movieDetails.MovieDetailsActivity
import com.example.user.moviechallengekotlin.R
import kotlinx.android.synthetic.main.activity_main.*

class MovieListActivity : AppCompatActivity(), MovieList.View {

    private lateinit var listView: RecyclerView
    lateinit var adapter: RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>
    private val presenter = MovieListPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.title_movies)

        listView = moviesRV
        listView.layoutManager = GridLayoutManager(this, 2)

        presenter.getMovies()
    }

    fun seeMovieDetails(title: String?, overview: String?, posterPath: String?) {
        val i = Intent(this, MovieDetailsActivity::class.java)
        i.putExtra(MovieDetailsActivity.MOVIE_TITLE, title)
        i.putExtra(MovieDetailsActivity.MOVIE_OVERVIEW, overview)
        i.putExtra(MovieDetailsActivity.MOVIE_POSTER_PATH, posterPath)
        this.startActivity(i)
    }

    override fun displayMovies(movies: List<MovieListViewModel>) {
        adapter = MovieListAdapter(movies, this)
        listView.adapter = adapter
    }
}
