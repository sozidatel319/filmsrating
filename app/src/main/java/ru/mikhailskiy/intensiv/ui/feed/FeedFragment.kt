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
import kotlinx.android.synthetic.main.progressbar.*
import ru.mikhailskiy.intensiv.*
import ru.mikhailskiy.intensiv.data.vo.AllMoviesVo
import ru.mikhailskiy.intensiv.data.repository.AllMoviesRepository
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.domain.usecase.AllMoviesUseCase
import ru.mikhailskiy.intensiv.presentation.view.BaseView
import ru.mikhailskiy.intensiv.presentation.presenter.feed.FeedPresenter
import ru.mikhailskiy.intensiv.presentation.view.feed.ShowMoviesView
import timber.log.Timber


class FeedFragment : Fragment(), BaseView, ShowMoviesView {

    private val feedPresenter: FeedPresenter by lazy {
        FeedPresenter(AllMoviesUseCase(AllMoviesRepository()), this, this)
    }

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    private lateinit var upcomingMoviesList: List<MainCardContainer>
    private lateinit var popularMoviesList: List<MainCardContainer>
    private lateinit var nowPlayingMoviesList: List<MainCardContainer>

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

        feedPresenter.attachView(this)
        feedPresenter.getMovies()

        movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }
        search_toolbar.onTextChangedObservable
            .doOnNext {
                openSearch(it)
            }
            .subscribe()
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
                            openMovieDetails(movie, R.id.movie_details_fragment)
                        }
                    }.toList()
            )
        )
    }

    override fun onStop() {
        super.onStop()
        adapter.clear()
        search_toolbar.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        feedPresenter.detachView()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun showMovies(movies: AllMoviesVo) {
        upcomingMoviesList = listOfMovies(R.string.upcoming, movies.upcomingMoviesList)
        nowPlayingMoviesList = listOfMovies(R.string.now_playing, movies.nowPlayingMoviesList)
        popularMoviesList = listOfMovies(R.string.popular, movies.popularMoviesList)
        println(upcomingMoviesList)
        println(nowPlayingMoviesList)
        println(popularMoviesList)
        movies_recycler_view.adapter =
            adapter.apply {
                addAll(upcomingMoviesList)
                addAll(nowPlayingMoviesList)
                addAll(popularMoviesList)
            }
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
}