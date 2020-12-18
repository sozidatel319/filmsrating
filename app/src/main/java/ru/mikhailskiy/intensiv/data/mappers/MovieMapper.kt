package ru.mikhailskiy.intensiv.data.mappers

import ru.mikhailskiy.intensiv.data.dto.MovieDTO
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.database.MovieEntity
import ru.mikhailskiy.intensiv.ui.watchlist.MoviePreviewItem

object MovieMapper {

    fun Movie.toMovieEntity(): MovieEntity {
        return MovieEntity(
            id = id,
            posterPath = posterPath,
            adult = adult,
            overview = overview,
            releaseDate = releaseDate,
            originalTitle = originalTitle,
            originalLanguage = originalLanguage,
            title = title,
            backDropPath = backDropPath,
            voteCount = voteCount,
            video = video,
            voteAverage = voteAverage,
            liked = liked
        )
    }

    fun MovieEntity.toMovie(): Movie {
        return Movie(
            posterPath = posterPath,
            adult = adult,
            overview = overview,
            releaseDate = releaseDate,
            genreIds = null,
            id = id,
            originalTitle = originalTitle,
            originalLanguage = originalLanguage,
            title = title,
            backDropPath = backDropPath,
            popularity = null,
            voteCount = voteCount,
            video = video,
            voteAverage = voteAverage,
            liked = liked
        )
    }

    fun List<MovieDTO>.toValueList(): List<Movie> {
        return this.map { it.toValueObject() }
    }

    @JvmName("toValueListMovieEntity")
    fun List<MovieEntity>.toValueList(): List<Movie> {
        return this.map { it.toMovie() }
    }

    @JvmName("toValueListMovie")
    fun List<Movie>.toValueList(): List<MoviePreviewItem> {
        return this.map { MoviePreviewItem(it, { movie -> }) }
    }

    fun MovieDTO.toValueObject(): Movie {
        return Movie(
            posterPath = fullPath,
            adult = adult,
            overview = overview,
            releaseDate = releaseDate,
            genreIds = null,
            id = id,
            originalTitle = originalTitle,
            originalLanguage = originalLanguage,
            title = title,
            backDropPath = fullBackDropPath,
            popularity = popularity,
            voteCount = voteCount,
            video = video,
            voteAverage = voteAverage,
            liked = liked
        )
    }

}