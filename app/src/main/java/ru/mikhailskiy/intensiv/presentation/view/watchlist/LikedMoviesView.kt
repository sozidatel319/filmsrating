package ru.mikhailskiy.intensiv.presentation.view.watchlist

import ru.mikhailskiy.intensiv.presentation.view.BaseView
import ru.mikhailskiy.intensiv.ui.watchlist.MoviePreviewItem

interface LikedMoviesView:BaseView {
    fun showLikedMoviesFromDb(movies: List<MoviePreviewItem>)
}