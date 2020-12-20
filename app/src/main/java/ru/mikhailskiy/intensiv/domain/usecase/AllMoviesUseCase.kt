package ru.mikhailskiy.intensiv.domain.usecase

import io.reactivex.Single
import ru.mikhailskiy.intensiv.addSchedulers
import ru.mikhailskiy.intensiv.data.vo.AllMoviesVo
import ru.mikhailskiy.intensiv.domain.repository.MovieRepository

class AllMoviesUseCase(private val repository: MovieRepository) {

    fun getAllMovies(): Single<AllMoviesVo> {
        return repository.getMovies()
            .addSchedulers()
    }
}