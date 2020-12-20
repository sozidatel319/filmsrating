package ru.mikhailskiy.intensiv.presentation.presenter.watchlist

import io.reactivex.disposables.CompositeDisposable
import ru.mikhailskiy.intensiv.domain.usecase.LikedMovieUseCase
import ru.mikhailskiy.intensiv.presentation.base.BasePresenter
import ru.mikhailskiy.intensiv.presentation.view.BaseView
import ru.mikhailskiy.intensiv.presentation.view.watchlist.LikedMoviesView

class WatchListPresenter(private val useCase: LikedMovieUseCase, private val baseView: BaseView, private val likedMoviesView: LikedMoviesView) :
    BasePresenter<BaseView>() {
    private val compositeDisposable = CompositeDisposable()

    fun getLikedMovies() {
        compositeDisposable.add(
            useCase.getLikedMovies()
            .doOnSubscribe { baseView.showLoading() }
            .doOnTerminate { baseView.hideLoading() }
            .subscribe(
                {
                    likedMoviesView.showLikedMoviesFromDb(it)
                },
                { t ->
                    baseView.showError(
                        t, t.toString()
                    )
                    baseView.showEmptyMovies()
                })
        )
    }
}