package com.example.user.moviechallengekotlin.scenes.movieList

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.example.user.moviechallengekotlin.scenes.movieDetails.MovieDetailsActivity
import com.example.user.moviechallengekotlin.R
import kotlinx.android.synthetic.main.activity_main.*

class MovieListActivity : AppCompatActivity(), MovieList.View {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private val presenter = MovieListPresenter(this)
    private lateinit var listView: RecyclerView
    lateinit var adapter: RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>
    private var isSearching: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.title_movies)

        viewPager = pager
        viewPager.adapter = MovieListPageAdapter(supportFragmentManager, this)

        tabLayout = mainTabLayout
        tabLayout.setupWithViewPager(viewPager)

        listView = moviesResultsRV
        listView.layoutManager = GridLayoutManager(this, 2)
    }

    fun displayMovieDetails(title: String?, overview: String?, posterPath: String?) {
        val i = Intent(this, MovieDetailsActivity::class.java)
        i.putExtra(MovieDetailsActivity.MOVIE_TITLE, title)
        i.putExtra(MovieDetailsActivity.MOVIE_OVERVIEW, overview)
        i.putExtra(MovieDetailsActivity.MOVIE_POSTER_PATH, posterPath)
        this.startActivity(i)
    }

    class MovieListPageAdapter(fm: FragmentManager, private val context: Context): FragmentPagerAdapter(fm) {

        companion object {
            const val PAGES = 4
        }

        override fun getCount(): Int = PAGES

        override fun getItem(position: Int): Fragment {
            if (position == 0) {
                return MovieListFragment.newInstance(MovieListFragment.GENRE_ID_ACTION)
            }

            if (position == 1) {
                return MovieListFragment.newInstance(MovieListFragment.GENRE_ID_DRAMA)
            }

            if (position == 2) {
                return MovieListFragment.newInstance(MovieListFragment.GENRE_ID_FANTASY)
            }

            return MovieListFragment.newInstance(MovieListFragment.GENRE_ID_FICTION)
        }

         override fun getPageTitle(position: Int): CharSequence {
             if (position == 0) {
                 return context.getString(R.string.label_acao)
             }

             if (position == 1) {
                 return context.getString(R.string.label_drama)
             }

             if (position == 2) {
                 return context.getString(R.string.label_fantasia)
             }
             return context.getString(R.string.label_ficcao)
         }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchView = menu?.findItem(R.id.search_menu)?.actionView as SearchView

        val queryTextChangeListener = object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = true

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    isSearching = false
                    mainContent.visibility = View.VISIBLE
                    moviesResultsRV.visibility = View.GONE
                } else {
                    isSearching = true
                    presenter.getMovieByName(newText)
                }
                return true
            }
        }

        searchView.setOnQueryTextListener(queryTextChangeListener)

        return true
    }

    override fun displayMovies(movies: List<MovieListViewModel>, totalPages: Int?) {
        if (isSearching) {
            adapter = MovieListAdapter(movies, this)
            listView.adapter = adapter
            mainContent.visibility = View.GONE
            moviesResultsRV.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        if (mainContent.visibility == View.GONE) {
            mainContent.visibility = View.VISIBLE
            moviesResultsRV.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }
}
