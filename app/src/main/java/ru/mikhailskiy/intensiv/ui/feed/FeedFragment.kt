package ru.mikhailskiy.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.Constants
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.Movie
import ru.mikhailskiy.intensiv.network.MovieApiClient
import ru.mikhailskiy.intensiv.ui.afterTextChanged
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

        search_toolbar.search_edit_text.afterTextChanged {
            Timber.d(it.toString())
            if (it.toString().length > 3) {
                openSearch(it.toString())
            }
        }

        // Используя Мок-репозиторий получаем фэйковый список фильмов
        val getUpcomingMovies =
            MovieApiClient.apiClient.getUpcomingMovies()
                .map { it.results }
                .subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    upcomingMoviesList = listOfMovies(R.string.upcoming, response)
                    movies_recycler_view.adapter =
                        adapter.apply {
                            addAll(upcomingMoviesList)
                        }
                }, {
                    Timber.e(it)
                })

        compositeDisposable.add(getUpcomingMovies)

        val getPopularMovies =
            MovieApiClient.apiClient.getPopularMovies()
                .map { it.results }
                .subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    popularMoviesList = listOfMovies(R.string.popular, response)

                    movies_recycler_view.adapter =
                        adapter.apply { addAll(popularMoviesList) }
                }, {
                    Timber.e(it)
                })
        compositeDisposable.add(getPopularMovies)

        val getNowPlayingMovies =
            MovieApiClient.apiClient.getNowPlayingMovies()
                .map { it.results }
                .subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    nowPlayingMoviesList = listOfMovies(R.string.now_playing, response)
                    movies_recycler_view.adapter =
                        adapter.apply { addAll(nowPlayingMoviesList) }
                },
                    {
                        Timber.e(it)
                    })
        compositeDisposable.add(getNowPlayingMovies)
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
        bundle.putString(Constants.TITLE, movie.title)
        bundle.putString(Constants.ABOUT_FILM, movie.overview)
        bundle.putString(Constants.FILM_POSTER, movie.fullBackDropPath)
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

    companion object {
        const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
    }
}