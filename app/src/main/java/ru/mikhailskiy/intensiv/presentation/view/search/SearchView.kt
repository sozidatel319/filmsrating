package ru.mikhailskiy.intensiv.presentation.view.search

import ru.mikhailskiy.intensiv.data.vo.Movie

interface SearchView {
    fun onMovieFounded(movieList: List<Movie>)
}