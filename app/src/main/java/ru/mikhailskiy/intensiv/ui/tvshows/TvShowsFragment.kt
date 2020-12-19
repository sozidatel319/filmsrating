package ru.mikhailskiy.intensiv.ui.tvshows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.progressbar.*
import kotlinx.android.synthetic.main.tv_shows_fragment.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.repository.AllTvShowsRepository
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.domain.usecase.AllTvShowUseCase
import ru.mikhailskiy.intensiv.openMovieDetails
import ru.mikhailskiy.intensiv.presentation.presenter.tvshow.TvShowPresenter
import ru.mikhailskiy.intensiv.progressBarVisible
import ru.mikhailskiy.intensiv.ui.feed.MainCardContainer
import ru.mikhailskiy.intensiv.ui.feed.MovieItem
import timber.log.Timber

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TvShowsFragment : Fragment(), TvShowPresenter.FeedView {

    private val tvShowPresenter: TvShowPresenter by lazy {
        TvShowPresenter(
            AllTvShowUseCase(
                AllTvShowsRepository()
            ), this
        )
    }
    private var param1: String? = null
    private var param2: String? = null

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    private lateinit var nowPlayingMoviesList: List<MainCardContainer>

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

        tvShowPresenter.attachView(this)
        tvShowPresenter.getPopularTvShows()

        tvShowsRecyclerview.adapter = adapter.apply { addAll(listOf()) }

    }

    private fun listOfMovies(
        description: Int,
        listMovies: List<Movie>
    ): List<MainCardContainer> {
        return listOf(
            MainCardContainer(
                description,
                listMovies
                    .map {
                        MovieItem(it) { movie ->
                            openMovieDetails(movie, R.id.movie_details_fragment)
                        }
                    }.toList()
            )
        )
    }

    override fun showPopularTvShows(tvShowList: List<Movie>) {
        nowPlayingMoviesList =
            listOfMovies(R.string.now_playing, tvShowList)
        tvShowsRecyclerview.adapter =
            adapter.apply { addAll(nowPlayingMoviesList) }
    }

    override fun showLoading() {
        progressBar.progressBarVisible(true)
    }

    override fun hideLoading() {
        progressBar.progressBarVisible(false)
    }

    override fun showEmptyMovies() {
    }

    override fun showError(throwable: Throwable, errorText: String) {
        Timber.e(throwable, errorText)
    }

    override fun onStop() {
        adapter.clear()
        super.onStop()
    }

    override fun onDestroy() {
        tvShowPresenter.detachView()
        super.onDestroy()
    }
}