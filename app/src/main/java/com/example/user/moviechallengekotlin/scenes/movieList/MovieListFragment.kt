package com.example.user.moviechallengekotlin.scenes.movieList

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import com.example.user.moviechallengekotlin.R
import kotlinx.android.synthetic.main.fragment_movie_list.*
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import com.example.user.moviechallengekotlin.db.FavoriteMovieDataBase
import com.example.user.moviechallengekotlin.scenes.movieDetails.MovieDetailsActivity


class MovieListFragment : Fragment(), MovieList.View {

    companion object {
        const val GENRE_ID_ACTION = 28
        const val GENRE_ID_DRAMA = 18
        const val GENRE_ID_FANTASY = 14
        const val GENRE_ID_FICTION = 878
        const val FAVORITE_FLAG = 1001
        const val ARG_GENRE_ID = "genre_id"
        var currentPage: Int = 1

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
    private lateinit var searchListView: RecyclerView
    private lateinit var presenter: MovieListPresenter
    private lateinit var scrollListener: MovieScrollListener
    private lateinit var adapter: MovieListAdapter
    private lateinit var searchAdapter: MovieListAdapter

    private var isSearching: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            genreId = it.getString(ARG_GENRE_ID)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MovieListPresenter(this, genreId!!, FavoriteMovieDataBase.getInstance(context))

        listView = moviesRV
        listView.layoutManager = GridLayoutManager(context, 2)

        searchListView = moviesResultsRV
        searchListView.layoutManager = GridLayoutManager(context, 2)

        adapter = MovieListAdapter(presenter.movieList, this)
        searchAdapter = MovieListAdapter(presenter.searchMovieList, this)

        listView.adapter = adapter
        searchListView.adapter = searchAdapter

        scrollListener = MovieScrollListener(listView.layoutManager as GridLayoutManager, presenter)
        listView.addOnScrollListener(scrollListener)

        if (genreId.equals(FAVORITE_FLAG.toString())) {
            presenter.getFavoriteMovies()
            swipeContainer?.setOnRefreshListener {
                presenter.getFavoriteMovies()
            }
        } else {
            presenter.getMovies()
            swipeContainer?.setOnRefreshListener {
                presenter.refreshList()
            }
        }
    }

    override fun displayMovies(movies: ArrayList<MovieListViewModel>, totalPages: Int?) {
        adapter.append(movies)
        scrollListener.isLoading = false
        scrollListener.isLastPage = currentPage == totalPages
        swipeContainer?.isRefreshing = false
    }

    override fun displayFavoriteMovies(movies: ArrayList<MovieListViewModel>) {
        adapter.swap(movies)
        scrollListener.isLoading = false
        scrollListener.isLastPage = true
        swipeContainer?.isRefreshing = false
    }

    override fun displaySearchMovies(movies: ArrayList<MovieListViewModel>, totalPages: Int?) {
        searchAdapter.swap(movies)
        scrollListener.isLoading = false
        scrollListener.isLastPage = currentPage == totalPages
        moviesRV.visibility = View.GONE
        moviesResultsRV.visibility = View.VISIBLE
        searchListView.adapter.notifyDataSetChanged()
    }

    class MovieScrollListener(val layoutManager: GridLayoutManager, private val presenter: MovieListPresenter): RecyclerView.OnScrollListener() {

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
                    presenter.getMovies()
                    isLoading = true
                }
            }
        }
    }

    override fun toggleFavoriteMovie(movie: MovieListViewModel) {
        presenter.toggleFavoriteMovie(movie)
    }

    override fun displayMovieDetails(movie: MovieListViewModel) {
        val i = Intent(context, MovieDetailsActivity::class.java)
        i.putExtra(MovieDetailsActivity.MOVIE_TITLE, movie.title)
        i.putExtra(MovieDetailsActivity.MOVIE_OVERVIEW, movie.overview)
        i.putExtra(MovieDetailsActivity.MOVIE_POSTER_PATH, movie.posterPath)
        this.startActivity(i)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.search_menu, menu)

        val searchView = menu?.findItem(R.id.search_menu)?.actionView as SearchView

        val queryTextChangeListener = object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = true

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    isSearching = false
                    moviesRV.visibility = View.VISIBLE
                    moviesResultsRV.visibility = View.GONE
                } else {
                    isSearching = true
                    presenter.getMovieByName(newText)
                }
                return true
            }
        }

        searchView.setOnQueryTextListener(queryTextChangeListener)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.search_menu, menu)
//
//        val searchView = menu?.findItem(R.id.search_menu)?.actionView as SearchView
//
//        val queryTextChangeListener = object: SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean = true
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                if (newText.isEmpty()) {
////                    isSearching = false
////                    pager.visibility = View.VISIBLE
////                    moviesResultsRV.visibility = View.GONE
//                } else {
//                    isSearching = true
////                    presenter.getMovieByName(newText)
//                }
//                return true
//            }
//        }
//
//        searchView.setOnQueryTextListener(queryTextChangeListener)
//
//        return true
//    }
}
