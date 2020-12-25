package ru.mikhailskiy.intensiv.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mikhailskiy.intensiv.domain.usecase.AllTvShowUseCase
import ru.mikhailskiy.intensiv.presentation.presenter.tvshow.TvShowsViewModel

class AllTvShowsViewModelFactory(private val useCase: AllTvShowUseCase) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TvShowsViewModel::class.java)) {
                return TvShowsViewModel(useCase) as T
            }
            throw IllegalArgumentException("Unknown $modelClass class")
        }
    }