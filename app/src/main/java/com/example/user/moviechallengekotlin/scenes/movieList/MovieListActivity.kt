package com.example.user.moviechallengekotlin.scenes.movieList

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
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
        mToolbar.title = getString(R.string.title_movies)
        setSupportActionBar(mToolbar)

        viewPager = pager
        viewPager.adapter = MovieListPageAdapter(supportFragmentManager, this)

        tabLayout = mainTabLayout
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(4)?.setIcon(R.drawable.ic_star)
    }
}
