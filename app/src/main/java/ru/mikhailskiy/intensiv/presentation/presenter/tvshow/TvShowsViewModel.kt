package ru.mikhailskiy.intensiv.presentation.presenter.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.mikhailskiy.intensiv.data.repository.AllTvShowsRepository
import ru.mikhailskiy.intensiv.data.vo.Movie

class TvShowsViewModel(private val repository: AllTvShowsRepository): ViewModel() {

    private val innerAllTvShowsLiveData = MutableLiveData<List<Movie>>()
    val allTvShowsLiveData = innerAllTvShowsLiveData as LiveData<List<Movie>>

    private val compositeDisposable = CompositeDisposable()

    fun getPopularTvShows() {
        compositeDisposable.add(
            repository.getPopularTvShows()
                .subscribe(
                    {
                    innerAllTvShowsLiveData.postValue(it)
                    },
                    {
                    })
        )
    }

    // Вызывается для очищения ресурсов
    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}