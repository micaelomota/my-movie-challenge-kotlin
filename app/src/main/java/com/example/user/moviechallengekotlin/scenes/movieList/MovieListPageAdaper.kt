package com.example.user.moviechallengekotlin.scenes.movieList

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.user.moviechallengekotlin.R

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