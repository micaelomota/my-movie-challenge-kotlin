package com.example.user.moviechallengekotlin.scenes.movieList

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

class MovieListAdapter(private var list: ArrayList<MovieListViewModel>, private val movieListView: MovieList.View): RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.movie_list_item, parent, false)
        return MovieViewHolder(v)
    }

    override fun onBindViewHolder(holder: MovieViewHolder?, position: Int) {
        holder?.description?.text =  list[position].title
        Picasso.get().load(RetrofitClient.IMAGE_BASE_URL + list[position].posterPath).into(holder?.poster)

        holder?.card?.setOnClickListener {
            movieListView.displayMovieDetails(list[position])
        }

        if (list[position].isFavorite) {
            holder?.favoriteBT?.setImageResource(R.drawable.ic_star)
        }

        holder?.favoriteBT?.setOnClickListener {
            if (list[position].isFavorite) {
                list[position].isFavorite = false
                (it as ImageButton).setImageResource(R.drawable.ic_empty_star)
            } else {
                list[position].isFavorite = true
                (it as ImageButton).setImageResource(R.drawable.ic_star)
            }
            movieListView.toggleFavoriteMovie(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val description: TextView = view.findViewById(R.id.txt_description)
        val poster: ImageView = view.findViewById(R.id.img_poster)
        val card: CardView = view.findViewById(R.id.card_movie)
        val favoriteBT: ImageButton = view.findViewById(R.id.favoriteBT)
    }

    fun append(newList:ArrayList<MovieListViewModel>) {
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun swap(newList:ArrayList<MovieListViewModel>) {
        list = newList
        notifyDataSetChanged()
    }
}