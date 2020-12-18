package ru.mikhailskiy.intensiv.data.vo

data class AllMoviesVo(
    val upcomingMoviesList: List<Movie>,
    val popularMoviesList: List<Movie>,
    val nowPlayingMoviesList: List<Movie>
)