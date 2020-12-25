package ru.mikhailskiy.intensiv.domain.usecase

import ru.mikhailskiy.intensiv.data.repository.AllTvShowsRepository
import ru.mikhailskiy.intensiv.data.vo.Movie

class AllTvShowUseCase(private val repository: AllTvShowsRepository) {

    suspend fun getPopularTvShows(): List<Movie> {
        return repository.getPopularTvShows()
    }
}