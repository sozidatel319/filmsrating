package ru.mikhailskiy.intensiv.presentation.presenter.watchlist

import io.reactivex.disposables.CompositeDisposable
import ru.mikhailskiy.intensiv.domain.usecase.AllMoviesUseCase
import ru.mikhailskiy.intensiv.presentation.base.BasePresenter
import ru.mikhailskiy.intensiv.ui.watchlist.MoviePreviewItem

class WatchListPresenter(private val useCase: AllMoviesUseCase, private val feedView: FeedView) :
    BasePresenter<WatchListPresenter.FeedView>() {
    private val compositeDisposable = CompositeDisposable()

    fun getLikedMovies() {
        compositeDisposable.add(
            useCase.getLikedMovies()
            .doOnSubscribe { feedView.showLoading() }
            .doOnTerminate { feedView.hideLoading() }
            .subscribe(
                {
                    feedView.showMoviesFromDb(it)
                },
                { t ->
                    feedView.showError(
                        t, t.toString()
                    )
                    feedView.showEmptyMovies()
                })
        )
    }

    interface FeedView {
        fun showMoviesFromDb(movies: List<MoviePreviewItem>)
        fun showLoading()
        fun hideLoading()
        fun showEmptyMovies()
        fun showError(throwable: Throwable, errorText: String)
    }
}