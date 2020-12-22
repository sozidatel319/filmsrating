package ru.mikhailskiy.intensiv.di

import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import ru.mikhailskiy.intensiv.data.repository.LikedMovieRepository
import ru.mikhailskiy.intensiv.domain.usecase.LikedMovieUseCase
import ru.mikhailskiy.intensiv.presentation.presenter.moviedetails.MovieDetailsPresenter
import ru.mikhailskiy.intensiv.presentation.presenter.watchlist.WatchListPresenter


@OptIn(KoinApiExtension::class)
val presentationModule = module {
    factory { MovieDetailsPresenter() }
    factory { WatchListPresenter() }
}

@OptIn(KoinApiExtension::class)
val domainModule = module {
    single { LikedMovieUseCase() }
    single { LikedMovieRepository() }
}

