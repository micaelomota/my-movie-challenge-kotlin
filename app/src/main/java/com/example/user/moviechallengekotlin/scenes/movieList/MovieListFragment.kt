package com.example.user.moviechallengekotlin.scenes.movieList

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.user.moviechallengekotlin.R
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment : Fragment(), MovieList.View {

    companion object {
        const val GENRE_ID_ACTION = 28
        const val GENRE_ID_DRAMA = 18
        const val GENRE_ID_FANTASY = 14
        const val GENRE_ID_FICTION = 878
        const val ARG_GENRE_ID = "genre_id"

        @JvmStatic
        fun newInstance(genreId: Int) =
                MovieListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_GENRE_ID, genreId.toString())
                    }
                }
    }

    private var genreId: String? = null

    private lateinit var listView: RecyclerView
    lateinit var adapter: RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>
    private val presenter = MovieListPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            genreId = it.getString(ARG_GENRE_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView = moviesRV
        listView.layoutManager = GridLayoutManager(context, 2)
        presenter.getMovies(genreId!!)
    }

    override fun displayMovies(movies: List<MovieListViewModel>) {
        adapter = MovieListAdapter(movies, context)
        listView.adapter = adapter
    }
}
