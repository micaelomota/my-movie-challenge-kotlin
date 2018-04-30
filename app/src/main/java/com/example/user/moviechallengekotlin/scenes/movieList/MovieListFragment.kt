package com.example.user.moviechallengekotlin.scenes.movieList

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.user.moviechallengekotlin.R
import kotlinx.android.synthetic.main.fragment_movie_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_GENRE_ID = "genre_id"

class MovieListFragment : Fragment(), MovieList.View {
    // TODO: Rename and change types of parameters
    private var genreId: String? = null
    private var listener: OnFragmentInteractionListener? = null

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

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        const val GENRE_ID_ACTION = 28
        const val GENRE_ID_DRAMA = 18
        const val GENRE_ID_FANTASY = 14
        const val GENRE_ID_FICTION = 878

        @JvmStatic
        fun newInstance(genreId: Int) =
                MovieListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_GENRE_ID, genreId.toString())
                    }
                }
    }

    override fun displayMovies(movies: List<MovieListViewModel>) {
        adapter = MovieListAdapter(movies, context)
        listView.adapter = adapter
    }
}
