package ru.mikhailskiy.intensiv.domain.usecase

import io.reactivex.Completable
import io.reactivex.Single
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.mikhailskiy.intensiv.addSchedulers
import ru.mikhailskiy.intensiv.data.repository.LikedMovieRepository
import ru.mikhailskiy.intensiv.database.MovieEntity
import ru.mikhailskiy.intensiv.ui.watchlist.MoviePreviewItem

@KoinApiExtension
class LikedMovieUseCase : KoinComponent {
    private val repository: LikedMovieRepository by inject()

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