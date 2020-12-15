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
import ru.mikhailskiy.intensiv.database.MovieEntity
import ru.mikhailskiy.intensiv.database.MovieDao

class MovieDetailsFragment : Fragment() {

    private val likedFilmsDao: MovieDao by lazy { MovieFinderApp.instance.database.likedFilmsDao }

    private var idFilm: Long? = null
    private var titleFilm: String? = null
    private var aboutFilm: String? = null
    private var path: String? = null
    private var movie: Movie? = null
    private var isLiked: Boolean = false
    private lateinit var likeButton: ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            movie = it.getParcelable(Constants.MOVIE) as? Movie
        }
        idFilm = movie?.id
        path = movie?.fullBackDropPath
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
        titleFilmTextView.text = titleFilm
        aboutFilmTextView.text = aboutFilm
        Picasso.get()
            .load(path)
            .into(filmPosterImageView)
        likeButton = requireView().findViewById(R.id.likeButton)
        this.setLikeButtonState()
        likeButton.setOnCheckedChangeListener(likeButtonListener())
        backButton.setOnClickListener(backButtonListener())

        super.onViewCreated(view, savedInstanceState)
    }

    private fun likeButtonListener() = CompoundButton.OnCheckedChangeListener { button, _ ->

        when (button.isChecked) {
            true -> {
                likedFilmsDao.insertMovie(
                    MovieEntity(
                        id = idFilm,
                        posterPath = path,
                        adult = false,
                        overview = "",
                        releaseDate = "",
                        originalTitle = titleFilm,
                        originalLanguage = "",
                        title = titleFilm,
                        backDropPath = "",
                        voteCount = null,
                        video = null,
                        voteAverage = 0.0,
                        liked = true
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


    private fun setLikeButtonState() {
        val findLikedFilmDisposable = movie?.id?.let { it ->
            likedFilmsDao.findLikedFilm(it)
                .addSchedulers()
                .subscribe { movieEntity ->
                    isLiked = movieEntity.liked == true
                    likeButton.isChecked = isLiked
                }
        }

    }
}