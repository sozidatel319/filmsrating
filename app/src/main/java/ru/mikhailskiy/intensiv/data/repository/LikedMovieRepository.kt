package ru.mikhailskiy.intensiv.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.mikhailskiy.intensiv.MovieFinderApp
import ru.mikhailskiy.intensiv.data.mappers.MovieMapper.toValueList
import ru.mikhailskiy.intensiv.database.MovieDao
import ru.mikhailskiy.intensiv.database.MovieEntity
import ru.mikhailskiy.intensiv.domain.repository.LikedMovieRepository
import ru.mikhailskiy.intensiv.ui.watchlist.MoviePreviewItem

class LikedMovieRepository : LikedMovieRepository {
    private val database: MovieDao = MovieFinderApp.instance.database.likedFilmsDao

    override fun getLikedMovies(): Single<List<MoviePreviewItem>> {
        return database.getAllLikedMovies()
            .map {
                it.toValueList()
            }
            .map { it.toValueList() }
    }

    override fun putLikedMovieToDatabase(movieEntity: MovieEntity): Completable {
        return database.insertMovie(movieEntity)
    }

    override fun deleteLikedMovieFromDataBase(id: Long): Completable {
        return database.deleteMovieById(id)
    }

    override fun findLikedMovieByIdInDatabase(id: Long): Single<Boolean> {
        return database.findLikedFilm(id)
    }
}