package ru.mikhailskiy.intensiv.presentation.presenter.tvshow

import io.reactivex.disposables.CompositeDisposable
import ru.mikhailskiy.intensiv.domain.usecase.AllTvShowUseCase
import ru.mikhailskiy.intensiv.presentation.base.BasePresenter
import ru.mikhailskiy.intensiv.presentation.view.BaseView
import ru.mikhailskiy.intensiv.presentation.view.tvshow.PopularTvShowView

class TvShowPresenter(
    private val useCase: AllTvShowUseCase,
    private val baseView: BaseView,
    private val popularTvShowView: PopularTvShowView
) :
    BasePresenter<BaseView>() {
    private val compositeDisposable = CompositeDisposable()

    fun getPopularTvShows() {
        compositeDisposable.add(
            useCase.getPopularTvShows()
                .doOnSubscribe { baseView.showLoading() }
                .doOnTerminate { baseView.hideLoading() }
                .subscribe(
                    {
                        popularTvShowView.showPopularTvShows(it)
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