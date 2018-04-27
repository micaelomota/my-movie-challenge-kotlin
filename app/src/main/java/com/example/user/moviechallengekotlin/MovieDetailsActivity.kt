package com.example.user.moviechallengekotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.user.moviechallengekotlin.api.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_TITLE = "title"
        const val MOVIE_OVERVIEW = "overview"
        const val MOVIE_POSTER_PATH = "posterPath"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val title = intent.extras.getString(MOVIE_TITLE)
        val overview = intent.extras.getString(MOVIE_OVERVIEW)
        val posterPath = intent.extras.getString(MOVIE_POSTER_PATH)

        val overviewTx = detailTX
        val poster = posterIMG

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = title
        overviewTx.text = overview
        Picasso.get().load(RetrofitClient.IMAGE_BASE_URL + posterPath).into(poster)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}

