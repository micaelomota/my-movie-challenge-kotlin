package com.example.user.moviechallengekotlin.scenes.movieList

import android.content.Context
import android.media.Image
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.user.moviechallengekotlin.R
import com.example.user.moviechallengekotlin.connection.RetrofitClient
import com.squareup.picasso.Picasso

class MovieListAdapter(val list: List<MovieListViewModel>, val context: Context): RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.movie_list_item, parent, false)
        return MovieViewHolder(v)
    }

    override fun onBindViewHolder(holder: MovieViewHolder?, position: Int) {
        holder?.description?.text =  list[position].title
        Picasso.get().load(RetrofitClient.IMAGE_BASE_URL + list[position].posterPath).into(holder?.poster)
        holder?.card?.setOnClickListener {
            (context as MovieListActivity).displayMovieDetails(list[position].title, list[position].overview, list[position].posterPath)
        }

        holder?.favoriteBT?.setOnClickListener {
            (it as ImageButton).setImageResource(R.drawable.ic_star)
        }


    }

    override fun getItemCount(): Int = list.size

    class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val description = view.findViewById<TextView>(R.id.txt_description)
        val poster = view.findViewById<ImageView>(R.id.img_poster)
        val card = view.findViewById<CardView>(R.id.card_movie)
        val favoriteBT = view.findViewById<ImageButton>(R.id.favoriteBT)
    }
}