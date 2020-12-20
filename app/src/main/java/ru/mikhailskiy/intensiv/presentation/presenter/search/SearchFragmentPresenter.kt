package ru.mikhailskiy.intensiv.presentation.presenter.search

import io.reactivex.disposables.CompositeDisposable
import ru.mikhailskiy.intensiv.domain.usecase.FindMovieUseCase
import ru.mikhailskiy.intensiv.presentation.base.BasePresenter
import ru.mikhailskiy.intensiv.presentation.view.BaseView
import ru.mikhailskiy.intensiv.presentation.view.search.SearchView

class SearchFragmentPresenter(
    private val useCase: FindMovieUseCase,
    private val baseView: BaseView,
    private val searchView: SearchView
) :
    BasePresenter<BaseView>() {

    private val compositeDisposable = CompositeDisposable()

    fun searchMovie(text: String) {
        compositeDisposable.add(
            useCase.foundMovieInWeb(text)
                .doOnSubscribe { baseView.showLoading() }
                .doOnTerminate { baseView.hideLoading() }
                .subscribe({
                searchView.onMovieFounded(it)
            }, {
                baseView.showError(it, it.toString())
            })
        )
    }
}
