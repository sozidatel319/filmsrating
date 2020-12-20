package ru.mikhailskiy.intensiv.domain.repository

import io.reactivex.Observable
import ru.mikhailskiy.intensiv.data.vo.Movie

interface FindMovieRepository {

    fun findMovieInWeb(text: String): Observable<List<Movie>>

}