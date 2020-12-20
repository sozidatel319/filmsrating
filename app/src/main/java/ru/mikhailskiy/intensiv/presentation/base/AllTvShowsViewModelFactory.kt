package ru.mikhailskiy.intensiv.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mikhailskiy.intensiv.data.repository.AllTvShowsRepository
import ru.mikhailskiy.intensiv.presentation.presenter.tvshow.TvShowsViewModel

class AllTvShowsViewModelFactory(private val repository: AllTvShowsRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TvShowsViewModel::class.java)) {
                return TvShowsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }