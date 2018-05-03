package com.example.user.moviechallengekotlin.scenes.movieList

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.user.moviechallengekotlin.R
import kotlinx.android.synthetic.main.fragment_movie_list.*
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.support.v7.widget.GridLayoutManager


class FavoriteListFragment : Fragment(), MovieList.View {

    companion object {
        const val GENRE_ID_ACTION = 99
        const val ARG_GENRE_ID = "genre_id"
        var currentPage: Int = 1

        @JvmStatic
        fun newInstance(genreId: Int) =
                FavoriteListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_GENRE_ID, genreId.toString())
                    }
                }
    }

    private var genreId: String? = null
    private lateinit var listView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>
    private val presenter = MovieListPresenter(this)
    private lateinit var scrollListener: MovieScrollListener
    private var movieList: MutableList<MovieListViewModel> = arrayListOf()

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

        movieList.clear()
        adapter = MovieListAdapter(movieList, context)
        listView.adapter = adapter

        scrollListener = MovieScrollListener(listView.layoutManager as GridLayoutManager, presenter, genreId!!)
        listView.addOnScrollListener(scrollListener)

        presenter.getMovies(genreId!!, 1)
    }

    override fun displayMovies(movies: List<MovieListViewModel>, totalPages: Int?) {
        movieList.addAll(movies)
        listView.adapter.notifyDataSetChanged()
        scrollListener.isLoading = false
        scrollListener.isLastPage = currentPage == totalPages
    }

    class MovieScrollListener(val layoutManager: GridLayoutManager, private val presenter: MovieListPresenter, private val genreId: String): RecyclerView.OnScrollListener() {

        var isLoading: Boolean = false
        var isLastPage: Boolean = false

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            if (!isLoading && !isLastPage) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    presenter.getMovies(genreId, ++currentPage)
                    isLoading = true
                }
            }
        }
    }
}
