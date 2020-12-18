package ru.mikhailskiy.intensiv.presentation.presenter.search

import io.reactivex.disposables.CompositeDisposable
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.domain.usecase.AllMoviesUseCase
import ru.mikhailskiy.intensiv.presentation.base.BasePresenter

class SearchFragmentPresenter(
    private val useCase: AllMoviesUseCase,
    private val feedView: FeedView
) :
    BasePresenter<SearchFragmentPresenter.FeedView>() {

    private val compositeDisposable = CompositeDisposable()

    fun searchMovie(text: String) {
        compositeDisposable.add(
            useCase.foundMovieInWeb(text)
                .doOnSubscribe { feedView.showLoading() }
                .doOnTerminate { feedView.hideLoading() }
                .subscribe({
                feedView.onMovieFounded(it)
            }, {
                feedView.onError(it, it.toString())
            })
        )
    }

    interface FeedView {
        fun onMovieFounded(movieList: List<Movie>)
        fun onError(throwable: Throwable, textError: String)
        fun showLoading()
        fun hideLoading()
        fun showEmptyMovies()
    }
}
