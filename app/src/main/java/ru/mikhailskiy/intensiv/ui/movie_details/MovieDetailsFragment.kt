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
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.repository.AllMoviesRepository
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.domain.usecase.AllMoviesUseCase
import ru.mikhailskiy.intensiv.presentation.presenter.moviedetails.MovieDetailsPresenter

class MovieDetailsFragment : Fragment(), MovieDetailsPresenter.FeedView {

    private val movieDetailsPresenter: MovieDetailsPresenter by lazy {
        MovieDetailsPresenter(
            AllMoviesUseCase(AllMoviesRepository()),
            this
        )
    }

    private var idFilm: Long? = null
    private var titleFilm: String? = null
    private var aboutFilm: String? = null
    private var path: String? = null
    private var movie: Movie? = null
    private lateinit var likeButton: ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            movie = it.getParcelable(Constants.MOVIE) as? Movie
        }
        idFilm = movie?.id
        path = movie?.backDropPath
        aboutFilm = movie?.overview
        titleFilm = movie?.title
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieDetailsPresenter.attachView(this)
        titleFilmTextView.text = titleFilm
        aboutFilmTextView.text = aboutFilm
        Picasso.get()
            .load(path)
            .into(filmPosterImageView)
        likeButton = requireView().findViewById(R.id.likeButton)
        movie?.let { movieDetailsPresenter.findLikedMovieInDatabase(it) }
        likeButton.setOnCheckedChangeListener(likeButtonListener())
        backButton.setOnClickListener(backButtonListener())

        super.onViewCreated(view, savedInstanceState)
    }

    private fun likeButtonListener() = CompoundButton.OnCheckedChangeListener { button, _ ->

        when (button.isChecked) {
            true -> {
                movie?.let { movieDetailsPresenter.putLikedMovieToDataBase(it) }
            }
            else -> {
                movie?.let { movieDetailsPresenter.deleteLikedMovieFromDataBase(it) }
            }
        }
    }

    private fun backButtonListener() = View.OnClickListener {
        requireActivity().onBackPressed()
    }

    override fun movieInDatabase(isExist: Boolean) {
        likeButton.isChecked = isExist
    }
}