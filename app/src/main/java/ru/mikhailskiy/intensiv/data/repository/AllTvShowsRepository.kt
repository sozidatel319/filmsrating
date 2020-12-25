package ru.mikhailskiy.intensiv.data.repository

import ru.mikhailskiy.intensiv.data.mappers.MovieMapper.toValueList
import ru.mikhailskiy.intensiv.data.network.MovieApiClient
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.domain.repository.TvShowsRepository

class AllTvShowsRepository : TvShowsRepository {

    override suspend fun getPopularTvShows(): List<Movie> =
        MovieApiClient.apiClient.getPopularTvShows()
            .results.toValueList()

}