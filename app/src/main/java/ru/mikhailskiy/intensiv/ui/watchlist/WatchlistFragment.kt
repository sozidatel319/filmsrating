package ru.mikhailskiy.intensiv.ui.watchlist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_watchlist.movies_recycler_view
import ru.mikhailskiy.intensiv.MovieFinderApp
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.addSchedulers
import ru.mikhailskiy.intensiv.data.Movie
import ru.mikhailskiy.intensiv.database.MovieDao

class WatchlistFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val database: MovieDao by lazy { MovieFinderApp.instance.database.likedFilmsDao }
    private lateinit var moviesList: List<MoviePreviewItem>

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_watchlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movies_recycler_view.layoutManager = GridLayoutManager(context, 4)
        movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }

        val getAllLikedMoviesDisposable = database.getAllLikedMovies()
            .addSchedulers()
            .subscribe { it ->
                moviesList =
                    it.map { dto ->
                        MoviePreviewItem(
                            Movie(
                                posterPath = dto.posterPath,
                                adult = dto.adult,
                                overview = dto.overview,
                                releaseDate = dto.releaseDate,
                                genreIds = null,
                                id = dto.id,
                                originalTitle = dto.originalTitle,
                                originalLanguage = dto.originalLanguage,
                                title = dto.title,
                                backDropPath = dto.backDropPath,
                                popularity = null,
                                voteCount = dto.voteCount,
                                video = dto.video,
                                voteAverage = dto.voteAverage,
                                liked = dto.liked
                            )
                        ) { movie -> }
                    }.toList()
                movies_recycler_view.adapter = adapter.apply { addAll(moviesList) }
            }
        /* MockRepository.getMovies().map {
             MoviePreviewItem(
                 it
             ) { movie -> }
         }.toList()*/

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            WatchlistFragment().apply {
            }
    }
}