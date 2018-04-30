package com.example.user.moviechallengekotlin.scenes.movieList

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import com.example.user.moviechallengekotlin.scenes.movieDetails.MovieDetailsActivity
import com.example.user.moviechallengekotlin.R
import kotlinx.android.synthetic.main.activity_main.*

class MovieListActivity : AppCompatActivity(), MovieListFragment.OnFragmentInteractionListener {


    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.title_movies)
        supportActionBar?.navigationMode

        viewPager = pager
        val tabListener = object : ActionBar.TabListener {
            override fun onTabSelected(tab: ActionBar.Tab, ft: FragmentTransaction) {
                // show the given tab
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: ActionBar.Tab, ft: FragmentTransaction) {
                // hide the given tab
            }

            override fun onTabReselected(tab: ActionBar.Tab, ft: FragmentTransaction) {
                // probably ignore this event
            }
        }


        supportActionBar?.navigationMode = ActionBar.NAVIGATION_MODE_TABS
        supportActionBar?.addTab(supportActionBar?.newTab()?.setText(R.string.label_acao)?.setTabListener(tabListener))
        supportActionBar?.addTab(supportActionBar?.newTab()?.setText(R.string.label_drama)?.setTabListener(tabListener))
        supportActionBar?.addTab(supportActionBar?.newTab()?.setText(R.string.label_fantasia)?.setTabListener(tabListener))
        supportActionBar?.addTab(supportActionBar?.newTab()?.setText(R.string.label_ficcao)?.setTabListener(tabListener))


        viewPager.adapter = MovieListPageAdapter(supportFragmentManager)

    }

    fun displayMovieDetails(title: String?, overview: String?, posterPath: String?) {
        val i = Intent(this, MovieDetailsActivity::class.java)
        i.putExtra(MovieDetailsActivity.MOVIE_TITLE, title)
        i.putExtra(MovieDetailsActivity.MOVIE_OVERVIEW, overview)
        i.putExtra(MovieDetailsActivity.MOVIE_POSTER_PATH, posterPath)
        this.startActivity(i)
    }



    class MovieListPageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

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

            if (position == 3) {
                return MovieListFragment.newInstance(MovieListFragment.GENRE_ID_FICTION)
            }
            return Fragment()
        }
    }

    override fun onFragmentInteraction(uri: Uri) {

    }

}
