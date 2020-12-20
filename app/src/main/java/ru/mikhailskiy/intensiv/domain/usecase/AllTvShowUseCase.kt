package ru.mikhailskiy.intensiv.domain.usecase

import io.reactivex.Single
import ru.mikhailskiy.intensiv.addSchedulers
import ru.mikhailskiy.intensiv.data.repository.AllTvShowsRepository
import ru.mikhailskiy.intensiv.data.vo.Movie

class AllTvShowUseCase(private val repository: AllTvShowsRepository) {

    fun getPopularTvShows(): Single<List<Movie>> {
        return repository.getPopularTvShows()
            .addSchedulers()
    }
}