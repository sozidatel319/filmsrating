package ru.mikhailskiy.intensiv.presentation.presenter.tvshow

import io.reactivex.disposables.CompositeDisposable
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.domain.usecase.AllTvShowUseCase
import ru.mikhailskiy.intensiv.presentation.base.BasePresenter

class TvShowPresenter(private val useCase: AllTvShowUseCase, private val feedView: FeedView) :
BasePresenter<TvShowPresenter.FeedView>() {
    private val compositeDisposable = CompositeDisposable()

    fun getPopularTvShows() {
        compositeDisposable.add(
            useCase.getPopularTvShows()
            .doOnSubscribe { feedView.showLoading() }
            .doOnTerminate { feedView.hideLoading() }
            .subscribe(
                {
                    feedView.showPopularTvShows(it)
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
        fun showPopularTvShows(tvShowList: List<Movie>)
        fun showLoading()
        fun hideLoading()
        fun showEmptyMovies()
        fun showError(throwable: Throwable, errorText: String)
    }
}