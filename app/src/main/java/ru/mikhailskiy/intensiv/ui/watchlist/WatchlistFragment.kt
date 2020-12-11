package ru.mikhailskiy.intensiv.ui.watchlist

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
    private val database: MovieDao = MovieFinderApp.instance?.database?.likedFilmsDao!!
    private lateinit var moviesList: List<MoviePreviewItem>

    val adapter by lazy {
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
                        val path = dto.posterPath
                        MoviePreviewItem(
                            Movie(
                                dto.posterPath, dto.adult, dto.overview,
                                dto.releaseDate, null, dto.id, dto.originalTitle,
                                dto.originalLanguage, dto.title, dto.backDropPath, null,
                                dto.voteCount, dto.video, dto.voteAverage, dto.liked
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