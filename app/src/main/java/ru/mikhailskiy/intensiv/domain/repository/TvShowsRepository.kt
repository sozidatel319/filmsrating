package ru.mikhailskiy.intensiv.domain.repository

import io.reactivex.Single
import ru.mikhailskiy.intensiv.data.vo.Movie

interface TvShowsRepository {

   suspend fun getPopularTvShows(): List<Movie>
}