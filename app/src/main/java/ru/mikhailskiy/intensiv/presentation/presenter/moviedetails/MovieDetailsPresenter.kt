package ru.mikhailskiy.intensiv.presentation.presenter.moviedetails

import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.mikhailskiy.intensiv.data.mappers.MovieMapper.toMovieEntity
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.domain.usecase.LikedMovieUseCase
import ru.mikhailskiy.intensiv.presentation.base.BasePresenter
import ru.mikhailskiy.intensiv.presentation.view.movie_details.MovieDetailsView

@KoinApiExtension
class MovieDetailsPresenter :
    BasePresenter<MovieDetailsView>(), KoinComponent {
    private val useCase: LikedMovieUseCase by inject()
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
                .subscribe { isExist ->
                    view?.movieInDatabase(isExist)
                }
        )
    }
}