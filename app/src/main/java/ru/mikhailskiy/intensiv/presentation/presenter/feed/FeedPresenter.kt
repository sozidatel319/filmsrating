package ru.mikhailskiy.intensiv.presentation.presenter.feed

import io.reactivex.disposables.CompositeDisposable
import ru.mikhailskiy.intensiv.data.vo.AllMoviesVo
import ru.mikhailskiy.intensiv.domain.usecase.AllMoviesUseCase
import ru.mikhailskiy.intensiv.presentation.base.BasePresenter

class FeedPresenter(private val useCase: AllMoviesUseCase,private val feedView: FeedView) :
    BasePresenter<FeedPresenter.FeedView>() {
    private val compositeDisposable = CompositeDisposable()

    fun getMovies() {
       compositeDisposable.add(
           useCase.getAllMovies()
            .doOnSubscribe { feedView.showLoading() }
            .doOnTerminate { feedView.hideLoading() }
            .subscribe(
                {
                    feedView.showMovies(it)
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
        fun showMovies(movies: AllMoviesVo)
        fun showLoading()
        fun hideLoading()
        fun showEmptyMovies()
        fun showError(throwable: Throwable, errorText: String)
    }
}