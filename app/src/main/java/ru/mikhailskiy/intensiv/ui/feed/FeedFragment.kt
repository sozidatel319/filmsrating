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
import ru.mikhailskiy.intensiv.*
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
                feedProgressBar.progressBarVisible(true)
            }
            .doOnTerminate {
                feedProgressBar.progressBarVisible(false)
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

    override fun onStop() {
        super.onStop()
        search_toolbar.clear()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }
}