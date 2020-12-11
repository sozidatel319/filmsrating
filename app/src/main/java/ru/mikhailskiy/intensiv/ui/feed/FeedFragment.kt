package ru.mikhailskiy.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.Constants
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.addSchedulers
import ru.mikhailskiy.intensiv.data.Movie
import ru.mikhailskiy.intensiv.data.MovieResult
import ru.mikhailskiy.intensiv.data.MoviesResponse
import ru.mikhailskiy.intensiv.network.MovieApiClient
import timber.log.Timber


class FeedFragment : Fragment() {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    private lateinit var upcomingMoviesList: List<MainCardContainer>
    private lateinit var popularMoviesList: List<MainCardContainer>
    private lateinit var nowPlayingMoviesList: List<MainCardContainer>
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }
        search_toolbar.onTextChangedObservable
            .doOnNext {
                openSearch(it)
            }
            .subscribe()

        val getUpcomingMovies =
            MovieApiClient.apiClient
                .getUpcomingMovies()

        val getPopularMovies =
            MovieApiClient.apiClient
                .getPopularMovies()

        val getNowPlayingMovies =
            MovieApiClient.apiClient
                .getNowPlayingMovies()

        val resultOfResponse = Single
            .zip(
                getUpcomingMovies, getPopularMovies, getNowPlayingMovies,
                Function3<MoviesResponse, MoviesResponse, MoviesResponse, MovieResult> { upcomingList, popularList, nowPlayingList ->
                    MovieResult(upcomingList.results, popularList.results, nowPlayingList.results)
                })
            .addSchedulers()
            .doOnSubscribe {
                progressBarVisibility(true)
            }
            .doOnTerminate {
                progressBarVisibility(false)
            }
            .subscribe({
                upcomingMoviesList = listOfMovies(R.string.upcoming, it.upcomingMoviesList)
                nowPlayingMoviesList = listOfMovies(R.string.now_playing, it.nowPlayingMoviesList)
                popularMoviesList = listOfMovies(R.string.popular, it.popularMoviesList)
                movies_recycler_view.adapter =
                    adapter.apply {
                        addAll(upcomingMoviesList)
                        addAll(nowPlayingMoviesList)
                        addAll(popularMoviesList)
                    }
            }, {
                Timber.e(it)
            })
        compositeDisposable.add(resultOfResponse)
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
        movie.id?.let { bundle.putLong(Constants.ID_FILM, it) }
        bundle.putString(Constants.TITLE, movie.title)
        bundle.putString(Constants.ABOUT_FILM, movie.overview)
        bundle.putString(Constants.FILM_POSTER, movie.fullBackDropPath)
        bundle.putParcelable(Constants.MOVIE, movie)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun openSearch(searchText: String) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        val bundle = Bundle()
        bundle.putString("search", searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    private fun listOfMovies(description: Int, listMovies: List<Movie>): List<MainCardContainer> {
        return listOf(
            MainCardContainer(
                description,
                listMovies
                    .map {
                        MovieItem(it) { movie ->
                            openMovieDetails(movie)
                        }
                    }.toList()
            )
        )
    }

    private fun progressBarVisibility(visible: Boolean) {
        feedProgressBar.visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onStop() {
        super.onStop()
        search_toolbar.clear()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    companion object {
        const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
    }
}