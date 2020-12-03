package ru.mikhailskiy.intensiv.ui.tvshows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.tv_shows_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mikhailskiy.intensiv.Constants
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.Movie
import ru.mikhailskiy.intensiv.data.MoviesResponse
import ru.mikhailskiy.intensiv.network.MovieApiClient
import ru.mikhailskiy.intensiv.ui.feed.FeedFragment
import ru.mikhailskiy.intensiv.ui.feed.MainCardContainer
import ru.mikhailskiy.intensiv.ui.feed.MovieItem
import timber.log.Timber

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TvShowsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    private var nowPlayingMoviesList: List<MainCardContainer>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tv_shows_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShowsRecyclerview.adapter = adapter.apply { addAll(listOf()) }

       /* val getPopularTvShows =
            MovieApiClient.apiClient.getPopularTvShows(FeedFragment.API_KEY, "ru")
        getPopularTvShows.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                nowPlayingMoviesList = listOf(
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
                tvShowsRecyclerview.adapter =
                    adapter.apply { addAll(nowPlayingMoviesList!!) }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Timber.d(t.toString())
            }
        })*/
    }

    fun openMovieDetails(movie: Movie) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        val bundle = Bundle()
        bundle.putString("title", movie.originalTitle)
        bundle.putString(Constants.FILM_POSTER,movie.backDropPath)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }
}