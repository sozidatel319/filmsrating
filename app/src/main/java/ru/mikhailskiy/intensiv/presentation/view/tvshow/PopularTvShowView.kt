package ru.mikhailskiy.intensiv.presentation.view.tvshow

import ru.mikhailskiy.intensiv.data.vo.Movie

interface PopularTvShowView {
    fun showPopularTvShows(tvShowList: List<Movie>)

}