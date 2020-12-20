package ru.mikhailskiy.intensiv.presentation.presenter.moviedetails

import io.reactivex.disposables.CompositeDisposable
import ru.mikhailskiy.intensiv.data.mappers.MovieMapper.toMovieEntity
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.domain.usecase.AllMoviesUseCase
import ru.mikhailskiy.intensiv.presentation.base.BasePresenter

class MovieDetailsPresenter(private val useCase: AllMoviesUseCase, private val feedView: FeedView) :
    BasePresenter<MovieDetailsPresenter.FeedView>() {
    private val compositeDisposable = CompositeDisposable()

    fun putLikedMovieToDataBase(movie: Movie) {
        compositeDisposable.add(
            useCase.putLikedMovieToDatabase(movie.toMovieEntity())
                .subscribe()
        )
    }

    fun deleteLikedMovieFromDataBase(movie: Movie) {
        compositeDisposable.add(
            useCase.deleteLikedMovieFromDatabase(movie.toMovieEntity())
                .subscribe()
        )
    }

    fun findLikedMovieInDatabase(movie: Movie) {
        compositeDisposable.add(
            useCase.foundLikedMovieByIdInDatabase(movie.id)
                .subscribe { exist ->
                    feedView.movieInDatabase(exist)
                }
        )
    }

    interface FeedView {
        fun movieInDatabase(isExist: Boolean)
    }
}