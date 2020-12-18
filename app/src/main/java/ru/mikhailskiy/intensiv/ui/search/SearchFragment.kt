package ru.mikhailskiy.intensiv.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.feed_fragment.movies_recycler_view
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.fragment_search.*
import ru.mikhailskiy.intensiv.*
import ru.mikhailskiy.intensiv.data.repository.AllMoviesRepository
import ru.mikhailskiy.intensiv.data.vo.Movie
import ru.mikhailskiy.intensiv.domain.usecase.AllMoviesUseCase
import ru.mikhailskiy.intensiv.presentation.presenter.search.SearchFragmentPresenter
import ru.mikhailskiy.intensiv.ui.feed.MainCardContainer
import ru.mikhailskiy.intensiv.ui.feed.MovieItem
import timber.log.Timber

class SearchFragment : Fragment(), SearchFragmentPresenter.FeedView {

    private val searchFragmentPresenter: SearchFragmentPresenter by lazy {
        SearchFragmentPresenter(
            AllMoviesUseCase(AllMoviesRepository()),
            this
        )
    }

    private lateinit var filmsFoundedList: List<MainCardContainer>
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchFragmentPresenter.attachView(this)

        val searchTerm = requireArguments().getString("search")
        search_toolbar.setText(searchTerm)
        movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }
        searchTerm?.let { it ->
            searchFragmentPresenter.searchMovie(it)
        }
    }

    override fun onDestroy() {
        searchFragmentPresenter.detachView()
        super.onDestroy()
    }

    override fun onMovieFounded(movieList: List<Movie>) {
        val movieItemList: ArrayList<MovieItem> = ArrayList()
        for (movie in movieList) {
            movieItemList.add(
                MovieItem(movie) { item ->
                    openMovieDetails(
                        item,
                        R.id.movie_details_fragment
                    )
                })
        }
        filmsFoundedList = listOf(MainCardContainer(R.string.recommended, movieItemList.toList()))
        movies_recycler_view.adapter =
            adapter.apply {
                addAll(filmsFoundedList)
            }
    }

    override fun onError(throwable: Throwable, textError: String) {
        Timber.e(throwable, textError)
    }

    override fun showLoading() {
        searchProgressBar.progressBarVisible(true)
    }

    override fun hideLoading() {
        searchProgressBar.progressBarVisible(false)
    }

    override fun showEmptyMovies() {
    }
}