package ru.mikhailskiy.intensiv.presentation.presenter.feed

import io.reactivex.disposables.CompositeDisposable
import ru.mikhailskiy.intensiv.domain.usecase.AllMoviesUseCase
import ru.mikhailskiy.intensiv.presentation.view.BaseView
import ru.mikhailskiy.intensiv.presentation.base.BasePresenter
import ru.mikhailskiy.intensiv.presentation.view.feed.ShowMoviesView

class FeedPresenter(
    private val useCase: AllMoviesUseCase,
    private val baseView: BaseView,
    private val showMoviesView: ShowMoviesView
) :
    BasePresenter<BaseView>() {
    private val compositeDisposable = CompositeDisposable()

    fun getMovies() {
        compositeDisposable.add(
            useCase.getAllMovies()
                .doOnSubscribe { baseView.showLoading() }
                .doOnTerminate { baseView.hideLoading() }
                .subscribe(
                    {
                        showMoviesView.showMovies(it)
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