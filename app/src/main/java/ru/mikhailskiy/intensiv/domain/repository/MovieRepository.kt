package ru.mikhailskiy.intensiv.domain.repository

import io.reactivex.Single
import ru.mikhailskiy.intensiv.data.vo.AllMoviesVo

interface MovieRepository {

    fun getMovies(): Single<AllMoviesVo>
}