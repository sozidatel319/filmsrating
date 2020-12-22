package ru.mikhailskiy.intensiv.presentation.presenter.watchlist

import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.mikhailskiy.intensiv.domain.usecase.LikedMovieUseCase
import ru.mikhailskiy.intensiv.presentation.base.BasePresenter
import ru.mikhailskiy.intensiv.presentation.view.watchlist.LikedMoviesView

@KoinApiExtension
class WatchListPresenter :
    BasePresenter<LikedMoviesView>(), KoinComponent {
    private val useCase: LikedMovieUseCase by inject()
    private val compositeDisposable = CompositeDisposable()

    fun getLikedMovies() {
        compositeDisposable.add(
            useCase.getLikedMovies()
                .doOnSubscribe { view?.showLoading() }
                .doOnTerminate { view?.hideLoading() }
                .subscribe(
                    {
                        view?.showLikedMoviesFromDb(it)
                    },
                    { t ->
                        view?.showError(
                            t, t.toString()
                        )
                        view?.showEmptyMovies()
                    })
        )
    }
}