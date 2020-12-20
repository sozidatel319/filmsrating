package ru.mikhailskiy.intensiv.domain.usecase

import io.reactivex.Completable
import io.reactivex.Single
import ru.mikhailskiy.intensiv.addSchedulers
import ru.mikhailskiy.intensiv.data.repository.LikedMovieRepository
import ru.mikhailskiy.intensiv.database.MovieEntity
import ru.mikhailskiy.intensiv.ui.watchlist.MoviePreviewItem

class LikedMovieUseCase(private val repository: LikedMovieRepository) {

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
        return repository.findLikedMovieByIdInDatabase(id)
            .addSchedulers()
    }
}