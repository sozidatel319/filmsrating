package ru.mikhailskiy.intensiv.presentation.view.feed

import ru.mikhailskiy.intensiv.data.vo.AllMoviesVo

interface ShowMoviesView {
    fun showMovies(movies: AllMoviesVo)
}