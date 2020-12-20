package ru.mikhailskiy.intensiv.data.repository

import io.reactivex.Observable
import ru.mikhailskiy.intensiv.data.mappers.MovieMapper.toValueList
import ru.mikhailskiy.intensiv.data.network.MovieApiClient
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.domain.repository.FindMovieRepository

class FindMovieRepository : FindMovieRepository {

    override fun findMovieInWeb(text: String): Observable<List<Movie>> {
        return MovieApiClient.apiClient.searchMovie(query = text)
            .map { it.results.toValueList() }
    }
}