package ru.mikhailskiy.intensiv.domain.usecase

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.mikhailskiy.intensiv.addSchedulers
import ru.mikhailskiy.intensiv.data.vo.AllMoviesVo
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.database.MovieEntity
import ru.mikhailskiy.intensiv.domain.repository.MovieRepository
import ru.mikhailskiy.intensiv.ui.watchlist.MoviePreviewItem

class AllMoviesUseCase(private val repository: MovieRepository) {

    fun getAllMovies(): Single<AllMoviesVo> {
        return repository.getMovies()
            .addSchedulers()
    }

    fun getLikedMovies(): Single<List<MoviePreviewItem>> {
        return repository.getLikedMovies()
            .addSchedulers()
    }

    fun putLikedMovieToDatabase(movieEntity: MovieEntity): Completable {
        return repository.putLikedMovieToDatabase(movieEntity)
            .addSchedulers()
    }

    fun deleteLikedMovieFromDatabase(movieEntity: MovieEntity): Completable {
        return repository.deleteLikedMovieFromDataBase(movieEntity.id)
            .addSchedulers()
    }

    fun foundLikedMovieByIdInDatabase(id: Long): Single<Boolean> {
        return repository.findFilmByIdInDatabase(id)
            .addSchedulers()
    }

    fun foundMovieInWeb(text: String):Observable<List<Movie>> {
        return repository.findMovieInWeb(text)
            .addSchedulers()
    }
}