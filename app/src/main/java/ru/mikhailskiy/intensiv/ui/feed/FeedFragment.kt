package ru.mikhailskiy.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.Constants
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.Movie
import ru.mikhailskiy.intensiv.data.MoviesResponse
import ru.mikhailskiy.intensiv.network.MovieApiClient
import ru.mikhailskiy.intensiv.ui.afterTextChanged
import timber.log.Timber

class FeedFragment : Fragment() {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    private var upcomingMoviesList: List<MainCardContainer>? = null
    private var popularMoviesList: List<MainCardContainer>? = null
    private var nowPlayingMoviesList: List<MainCardContainer>? = null

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

        // Добавляем recyclerView
        upcoming_movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }
        popular_movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }
        playing_now_movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }

        search_toolbar.search_edit_text.afterTextChanged {
            Timber.d(it.toString())
            if (it.toString().length > 3) {
                openSearch(it.toString())
            }
        }

        // Используя Мок-репозиторий получаем фэйковый список фильмов
        val getUpcomingMovies = MovieApiClient.apiClient.getUpcomingMovies(API_KEY, "ru")
        getUpcomingMovies.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                upcomingMoviesList = listOf(
                    MainCardContainer(
                        R.string.upcoming,
                        response.body()!!.results
                            .map {
                                MovieItem(it) { movie ->
                                    openMovieDetails(
                                        movie
                                    )
                                }
                            }.toList()
                    )
                )
                upcoming_movies_recycler_view.adapter =
                    adapter.apply { addAll(upcomingMoviesList!!) }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Timber.d(t.toString())
            }

        })

        val getPopularMovies = MovieApiClient.apiClient.getPopularMovies(API_KEY, "ru")
        getPopularMovies.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                popularMoviesList = listOf(
                    MainCardContainer(
                        R.string.recommended,
                        response.body()!!.results
                            .map {
                                MovieItem(it) { movie ->
                                    openMovieDetails(movie)
                                }
                            }.toList()
                    )
                )

                popular_movies_recycler_view.adapter = adapter.apply { addAll(popularMoviesList!!) }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Timber.d(t.toString())
            }
        })

        val getNowPlayingMovies = MovieApiClient.apiClient.getNowPlayingMovies(API_KEY, "ru")
        getNowPlayingMovies.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                nowPlayingMoviesList = listOf(
                    MainCardContainer(
                        R.string.now_playing,
                        response.body()!!.results
                            .map {
                                MovieItem(it) { movie ->
                                    openMovieDetails(movie)
                                }
                            }.toList()
                    )
                )

                playing_now_movies_recycler_view.adapter =
                    adapter.apply { addAll(nowPlayingMoviesList!!) }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Timber.d(t.toString())
            }

        })


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
        bundle.putString(Constants.TITLE_FILM, movie.title)
        bundle.putString(Constants.ABOUT_FILM, movie.overview)
        bundle.putString(Constants.FILM_POSTER, movie.backDropPath)
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