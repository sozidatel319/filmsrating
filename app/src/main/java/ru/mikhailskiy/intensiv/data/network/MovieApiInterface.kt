package ru.mikhailskiy.intensiv.data.network

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.data.dto.MoviesResponseDTO


interface MovieApiInterface {

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<MoviesResponseDTO>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru"
    ): Single<MoviesResponseDTO>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru"
    ): Single<MoviesResponseDTO>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru"
    ): Single<MoviesResponseDTO>

    @GET("movie/get-movie-details")
    fun getMovieDetails(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<MoviesResponseDTO>

    @GET("movie/get-movie-credits")
    fun getMovieCredits(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<MoviesResponseDTO>

    @GET("tv/popular")
    fun getPopularTvShows(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru"
    ): Single<MoviesResponseDTO>

    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("query") query: String
    ): Observable<MoviesResponseDTO>

}
