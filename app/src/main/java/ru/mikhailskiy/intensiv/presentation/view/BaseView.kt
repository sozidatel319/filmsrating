package ru.mikhailskiy.intensiv.presentation.view

interface BaseView {
        fun showLoading()
        fun hideLoading()
        fun showEmptyMovies()
        fun showError(throwable: Throwable, errorText: String)
}