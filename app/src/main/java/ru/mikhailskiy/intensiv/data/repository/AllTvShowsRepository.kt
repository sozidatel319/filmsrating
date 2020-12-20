package ru.mikhailskiy.intensiv.data.repository

import io.reactivex.Single
import ru.mikhailskiy.intensiv.addSchedulers
import ru.mikhailskiy.intensiv.data.mappers.MovieMapper.toValueList
import ru.mikhailskiy.intensiv.data.network.MovieApiClient
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.domain.repository.TvShowsRepository

class AllTvShowsRepository : TvShowsRepository {

    override fun getPopularTvShows(): Single<List<Movie>> =
        MovieApiClient.apiClient.getPopularTvShows()
            .map { it.results }
            .map { it.toValueList() }
            .addSchedulers()
}