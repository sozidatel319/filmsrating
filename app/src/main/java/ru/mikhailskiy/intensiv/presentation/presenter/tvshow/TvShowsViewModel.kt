package ru.mikhailskiy.intensiv.presentation.presenter.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.domain.usecase.AllTvShowUseCase

class TvShowsViewModel(private val useCase: AllTvShowUseCase) : ViewModel() {

    private val innerAllTvShowsLiveData = MutableLiveData<List<Movie>>()
    val allTvShowsLiveData = innerAllTvShowsLiveData as LiveData<List<Movie>>

    suspend fun getPopularTvShows() {
        viewModelScope.launch(Dispatchers.IO) {
            innerAllTvShowsLiveData.postValue(useCase.getPopularTvShows())
        }
    }
}