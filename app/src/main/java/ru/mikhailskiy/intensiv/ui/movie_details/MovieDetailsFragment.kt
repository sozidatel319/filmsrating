package ru.mikhailskiy.intensiv.ui.movie_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ToggleButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_details_fragment.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.mikhailskiy.intensiv.Constants
import ru.mikhailskiy.intensiv.MovieFinderApp
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.addSchedulers
import ru.mikhailskiy.intensiv.data.Movie
import ru.mikhailskiy.intensiv.database.MovieDTO
import ru.mikhailskiy.intensiv.database.MovieDao
import timber.log.Timber
import kotlin.properties.Delegates

class MovieDetailsFragment : Fragment() {

    private var likedFilmsDao: MovieDao = MovieFinderApp.instance?.database?.likedFilmsDao!!

    private var idFilm: Long? = null
    private var titleFilm: String? = null
    private var aboutFilm: String? = null
    private var path: String? = null
    private lateinit var movie: Movie
    private var isLiked: Boolean = false
    private lateinit var likeButton: ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idFilm = it.getLong(Constants.ID_FILM)
            titleFilm = it.getString(Constants.TITLE)
            aboutFilm = it.getString(Constants.ABOUT_FILM)
            path = it.getString(Constants.FILM_POSTER)
            movie = it.getParcelable(Constants.MOVIE)!!
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
            .load(path)
            .into(filmPosterImageView)
        likeButton = requireView().findViewById(R.id.likeButton)
        isFilmInDatabase()
        likeButton.setOnCheckedChangeListener(likeButtonListener())
        backButton.setOnClickListener(backButtonListener())

        super.onViewCreated(view, savedInstanceState)
    }

    private fun likeButtonListener() = CompoundButton.OnCheckedChangeListener { button, _ ->

        when (button.isChecked) {
            true -> {
                likedFilmsDao.insertMovie(
                    MovieDTO(
                        idFilm, path, false, "", "", titleFilm, "",
                        titleFilm, "", null, null, 0.0, true
                    )
                )
                    .addSchedulers()
                    .subscribe()
            }
            else -> {
                idFilm?.let {
                    likedFilmsDao.deleteMovieById(it)
                        .addSchedulers()
                        .subscribe()
                }
            }
        }
    }

    private fun backButtonListener() = View.OnClickListener {
        requireActivity().onBackPressed()
    }


    private fun isFilmInDatabase() {
        val findLikedFilmDisposable = movie.id?.let { likedFilmsDao.findLikedFilm(it) }
            ?.addSchedulers()
            ?.subscribe {
                isLiked = it.liked == true
                likeButton.isChecked = isLiked
            }
    }
}