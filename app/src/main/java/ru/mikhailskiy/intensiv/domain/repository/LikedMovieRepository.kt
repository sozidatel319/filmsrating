package ru.mikhailskiy.intensiv.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.mikhailskiy.intensiv.database.MovieEntity
import ru.mikhailskiy.intensiv.ui.watchlist.MoviePreviewItem

interface LikedMovieRepository {

    fun getLikedMovies(): Single<List<MoviePreviewItem>>

    fun putLikedMovieToDatabase(movieEntity: MovieEntity): Completable

    fun deleteLikedMovieFromDataBase(id:Long): Completable

    fun findLikedMovieByIdInDatabase(id: Long): Single<Boolean>
}