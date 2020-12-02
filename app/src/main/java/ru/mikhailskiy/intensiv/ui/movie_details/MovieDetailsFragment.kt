package ru.mikhailskiy.intensiv.ui.movie_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.mikhailskiy.intensiv.Constants
import ru.mikhailskiy.intensiv.R

class MovieDetailsFragment : Fragment() {

    private var titleFilm: String? = null
    private var aboutFilm: String? = null
    private var path: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            titleFilm = it.getString(Constants.TITLE_FILM)
            aboutFilm = it.getString(Constants.ABOUT_FILM)
            path = it.getString(Constants.FILM_POSTER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        titleFilmTextView.text = titleFilm
        aboutFilmTextView.text = aboutFilm
        Picasso.get()
            .load(Constants.HEAD_OF_PATH + path)
            .into(filmPosterImageView)
        super.onViewCreated(view, savedInstanceState)
    }
}