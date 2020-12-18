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
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.repository.AllMoviesRepository
import ru.mikhailskiy.intensiv.domain.usecase.AllMoviesUseCase
import ru.mikhailskiy.intensiv.presentation.presenter.watchlist.WatchListPresenter
import timber.log.Timber

class WatchlistFragment : Fragment(), WatchListPresenter.FeedView {

    private val watchListPresenter: WatchListPresenter by lazy {
        WatchListPresenter(
            AllMoviesUseCase(
                AllMoviesRepository()
            ), this
        )
    }

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
        watchListPresenter.attachView(this)
        watchListPresenter.getLikedMovies()
        movies_recycler_view.layoutManager = GridLayoutManager(context, 4)
        movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            WatchlistFragment().apply {
            }
    }

    override fun showMoviesFromDb(movies: List<MoviePreviewItem>) {
        movies_recycler_view.adapter = adapter.apply { addAll(movies) }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showEmptyMovies() {
    }

    override fun showError(throwable: Throwable, errorText: String) {
        Timber.e(throwable, errorText)
    }

    override fun onDestroy() {
        super.onDestroy()
        watchListPresenter.detachView()
    }
}