package ru.mikhailskiy.intensiv.data.repository

import io.reactivex.Single
import ru.mikhailskiy.intensiv.data.vo.AllMoviesVo
import ru.mikhailskiy.intensiv.data.network.MovieApiClient
import io.reactivex.functions.Function3
import ru.mikhailskiy.intensiv.data.mappers.MovieMapper.toValueList
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.domain.repository.MovieRepository

class AllMoviesRepository : MovieRepository {

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
}