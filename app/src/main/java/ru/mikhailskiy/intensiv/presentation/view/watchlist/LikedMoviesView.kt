package ru.mikhailskiy.intensiv.presentation.view.watchlist

import ru.mikhailskiy.intensiv.ui.watchlist.MoviePreviewItem

interface LikedMoviesView {
    fun showLikedMoviesFromDb(movies: List<MoviePreviewItem>)
}