package ru.mikhailskiy.intensiv.domain.usecase

import io.reactivex.Observable
import ru.mikhailskiy.intensiv.addSchedulers
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.domain.repository.FindMovieRepository

class FindMovieUseCase(private val repository: FindMovieRepository) {

    fun foundMovieInWeb(text: String): Observable<List<Movie>> {
        return repository.findMovieInWeb(text)
            .addSchedulers()
    }
}