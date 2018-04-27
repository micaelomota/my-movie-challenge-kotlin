package com.example.user.moviechallengekotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.user.moviechallengekotlin.api.RetrofitClient
import com.squareup.picasso.Picasso

class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val title = intent.extras.getString("title")
        val overview = intent.extras.getString("overview")
        val posterPath = intent.extras.getString("posterPath")

        findViewById<TextView>(R.id.txt_detail_overview).text = overview

        supportActionBar?.title = title
        val poster = findViewById<ImageView>(R.id.img_poster_details)

        Picasso.get().load(RetrofitClient.IMAGE_BASE_URL + posterPath).into(poster)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}

