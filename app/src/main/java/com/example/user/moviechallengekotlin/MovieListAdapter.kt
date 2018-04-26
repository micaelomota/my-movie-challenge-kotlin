package com.example.user.moviechallengekotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.user.moviechallengekotlin.api.RetrofitClient
import com.example.user.moviechallengekotlin.pojo.movielist.Result
import com.squareup.picasso.Picasso

class MovieListAdapter(val list: ArrayList<Result>, val context: Context): RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.movie_list_item, parent, false)
        return MovieViewHolder(v)
    }

    override fun onBindViewHolder(holder: MovieViewHolder?, position: Int) {
        holder?.description?.text =  list[position].title
        Picasso.get().load(RetrofitClient.IMAGE_BASE_URL + list[position].posterPath).into(holder?.poster)
    }

    override fun getItemCount(): Int = list.size

    class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val description = view.findViewById<TextView>(R.id.txt_description)
        val poster = view.findViewById<ImageView>(R.id.img_poster)
    }
}