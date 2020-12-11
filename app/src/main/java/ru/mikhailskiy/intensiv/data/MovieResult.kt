package ru.mikhailskiy.intensiv.data

data class MovieResult(
    val upcomingMoviesList: List<Movie>,
    val popularMoviesList: List<Movie>,
    val nowPlayingMoviesList: List<Movie>
)