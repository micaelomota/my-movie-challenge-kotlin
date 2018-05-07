package com.example.user.moviechallengekotlin.scenes.movieList

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.example.user.moviechallengekotlin.R
import kotlinx.android.synthetic.main.activity_main.*

class MovieListActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mToolbar = toolbar
        mToolbar?.title = getString(R.string.title_movies)
        setSupportActionBar(mToolbar)

        viewPager = pager
        viewPager.adapter = MovieListPageAdapter(supportFragmentManager, this)

        tabLayout = mainTabLayout
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(4)?.setIcon(R.drawable.ic_star)
    }

    class MovieListPageAdapter(fm: FragmentManager, private val context: Context): FragmentPagerAdapter(fm) {

        companion object {
            const val PAGES = 5
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

            return MovieListFragment.newInstance(MovieListFragment.FAVORITE_FLAG)
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

             if (position == 3) {
                 return context.getString(R.string.label_ficcao)
             }
             return ""
         }
    }
}
