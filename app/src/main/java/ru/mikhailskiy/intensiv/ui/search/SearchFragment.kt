package ru.mikhailskiy.intensiv.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Observable.fromIterable
import kotlinx.android.synthetic.main.feed_fragment.movies_recycler_view
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.fragment_search.*
import ru.mikhailskiy.intensiv.Constants
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.Movie
import ru.mikhailskiy.intensiv.network.MovieApiClient
import ru.mikhailskiy.intensiv.addSchedulers
import ru.mikhailskiy.intensiv.progressBarVisible
import ru.mikhailskiy.intensiv.ui.feed.MainCardContainer
import ru.mikhailskiy.intensiv.ui.feed.MovieItem
import timber.log.Timber

class SearchFragment : Fragment() {
    private lateinit var filmsFoundedList: List<MainCardContainer>
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
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchTerm = requireArguments().getString("search")
        search_toolbar.setText(searchTerm)
        movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }
        searchTerm?.let { it ->
            MovieApiClient.apiClient.searchMovie(query = it)
                .toObservable()
                .addSchedulers()
                .flatMap { moviesList -> fromIterable(moviesList.results) }
                .map { movie -> MovieItem(movie) { item -> openMovieDetails(item) } }.toList()
                .doOnSubscribe {
                    searchProgressBar.progressBarVisible(true)
                }
                .doOnTerminate {
                    searchProgressBar.progressBarVisible(false)
                }
                .subscribe({
                    filmsFoundedList = listOf(
                        MainCardContainer(
                            R.string.recommended,
                            it
                        )
                    )
                    movies_recycler_view.adapter =
                        adapter.apply {
                            addAll(filmsFoundedList)
                        }
                }, { error ->
                    Timber.e(error)
                })
        }
    }

    private fun openMovieDetails(movie: Movie) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        val bundle = Bundle()
        bundle.putParcelable(Constants.MOVIE,movie)
        bundle.putString(Constants.TITLE, movie.title)
        bundle.putString(Constants.ABOUT_FILM, movie.overview)
        bundle.putString(Constants.FILM_POSTER, movie.fullBackDropPath)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }
}