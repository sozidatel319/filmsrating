package ru.mikhailskiy.intensiv.ui.tvshows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.tv_shows_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.repository.AllTvShowsRepository
import ru.mikhailskiy.intensiv.domain.usecase.AllTvShowUseCase
import ru.mikhailskiy.intensiv.openMovieDetails
import ru.mikhailskiy.intensiv.presentation.base.AllTvShowsViewModelFactory
import ru.mikhailskiy.intensiv.presentation.presenter.tvshow.TvShowsViewModel
import ru.mikhailskiy.intensiv.ui.feed.MovieItem

class TvShowsFragment : Fragment() {

    private lateinit var tvShowsViewModel: TvShowsViewModel

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val allTvShowsViewModelFactory =
            AllTvShowsViewModelFactory(AllTvShowUseCase(AllTvShowsRepository()))

        tvShowsViewModel =
            ViewModelProvider(this, allTvShowsViewModelFactory).get(TvShowsViewModel::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            tvShowsViewModel.getPopularTvShows()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tv_shows_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tvShowsRecyclerview.adapter = adapter.apply { addAll(listOf()) }

        tvShowsViewModel.allTvShowsLiveData.observe(viewLifecycleOwner, Observer { list ->
            val moviesList =
                list.map { movie ->
                    MovieItem(movie) {
                        openMovieDetails(
                            movie,
                            R.id.movie_details_fragment
                        )
                    }
                }
                    .toList()
            tvShowsRecyclerview.adapter = adapter.apply { addAll(moviesList) }
        })

    }

    override fun onStop() {
        adapter.clear()
        GlobalScope.coroutineContext.cancel()
        super.onStop()
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}