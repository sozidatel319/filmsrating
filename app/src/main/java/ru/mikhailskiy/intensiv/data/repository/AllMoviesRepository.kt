package ru.mikhailskiy.intensiv.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.mikhailskiy.intensiv.data.vo.AllMoviesVo
import ru.mikhailskiy.intensiv.data.network.MovieApiClient
import io.reactivex.functions.Function3
import ru.mikhailskiy.intensiv.*
import ru.mikhailskiy.intensiv.data.mappers.MovieMapper.toValueList
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.database.MovieDao
import ru.mikhailskiy.intensiv.database.MovieEntity
import ru.mikhailskiy.intensiv.domain.repository.MovieRepository
import ru.mikhailskiy.intensiv.ui.watchlist.MoviePreviewItem


class AllMoviesRepository : MovieRepository {
    private val database: MovieDao = MovieFinderApp.instance.database.likedFilmsDao

    private val getUpcomingMovies =
        MovieApiClient.apiClient
            .getUpcomingMovies()
            .map {
                it.results
                    .toValueList()
            }

    private val getPopularMovies =
        MovieApiClient.apiClient
            .getPopularMovies()
            .map {
                it.results
                    .toValueList()
            }

    private val getNowPlayingMovies =
        MovieApiClient.apiClient
            .getNowPlayingMovies()
            .map {
                it.results
                    .toValueList()
            }

    override fun getMovies(): Single<AllMoviesVo> {
        return Single
            .zip(
                getUpcomingMovies, getPopularMovies, getNowPlayingMovies,
                Function3 { upcomingList: List<Movie>, popularList: List<Movie>, nowPlayingList: List<Movie> ->
                    AllMoviesVo(
                        upcomingList,
                        popularList,
                        nowPlayingList
                    )
                })
    }

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

    override fun findFilmByIdInDatabase(id: Long): Single<Boolean> {
        return database.findLikedFilm(id)
    }

    override fun findMovieInWeb(text: String): Observable<List<Movie>> {
        return MovieApiClient.apiClient.searchMovie(query = text)
            .map { it.results.toValueList() }
    }
}