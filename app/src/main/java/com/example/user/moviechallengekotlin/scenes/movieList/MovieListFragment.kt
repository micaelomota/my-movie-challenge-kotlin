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
    private var movieList: MutableList<MovieListViewModel> = arrayListOf()
    private var searchMovieList: MutableList<MovieListViewModel> = arrayListOf()
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
        presenter = MovieListPresenter(this, context)

        listView = moviesRV
        listView.layoutManager = GridLayoutManager(context, 2)

        searchListView = moviesResultsRV
        searchListView.layoutManager = GridLayoutManager(context, 2)

        movieList.clear()
        searchMovieList.clear()

        listView.adapter = MovieListAdapter(movieList, this)

        searchListView.adapter = MovieListAdapter(searchMovieList, this)

        scrollListener = MovieScrollListener(listView.layoutManager as GridLayoutManager, presenter, genreId!!)
        listView.addOnScrollListener(scrollListener)

        if (genreId.equals(FAVORITE_FLAG.toString())) {
            movieList.addAll(presenter.getAllFavoriteMovies())
            listView.adapter.notifyDataSetChanged()
        } else {
            presenter.getMovies(genreId!!, 1)
        }

        swipeContainer?.setOnRefreshListener {
            currentPage = 1
            movieList.clear()
            presenter.getMovies(genreId!!, 1)
        }
    }

    override fun displayMovies(movies: List<MovieListViewModel>, totalPages: Int?) {
        movieList.addAll(movies)
        listView.adapter.notifyDataSetChanged()
        scrollListener.isLoading = false
        scrollListener.isLastPage = currentPage == totalPages
        swipeContainer?.isRefreshing = false
    }

    override fun displaySearchMovies(movies: ArrayList<MovieListViewModel>, totalPages: Int?) {
        searchMovieList.clear()
        searchMovieList.addAll(movies)
        scrollListener.isLoading = false
        scrollListener.isLastPage = currentPage == totalPages
        moviesRV.visibility = View.GONE
        moviesResultsRV.visibility = View.VISIBLE
        searchListView.adapter.notifyDataSetChanged()
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

    override fun toggleFavoriteMovie(movie: MovieListViewModel) {
        if (presenter.getFavoriteMovie(movie.id) != null) {
            presenter.unsetFavoriteMovie(movie.id)
        } else {
            presenter.setFavoriteMovie(movie)
        }
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
