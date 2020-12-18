package ru.mikhailskiy.intensiv.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.mikhailskiy.intensiv.data.vo.AllMoviesVo
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.database.MovieEntity
import ru.mikhailskiy.intensiv.ui.watchlist.MoviePreviewItem

interface MovieRepository {

    fun getMovies(): Single<AllMoviesVo>

    fun getLikedMovies(): Single<List<MoviePreviewItem>>

    fun putLikedMovieToDatabase(movieEntity: MovieEntity): Completable

    fun deleteLikedMovieFromDataBase(id:Long):Completable

    fun findFilmByIdInDatabase(id: Long):Single<Boolean>

    fun findMovieInWeb(text: String): Observable<List<Movie>>
}