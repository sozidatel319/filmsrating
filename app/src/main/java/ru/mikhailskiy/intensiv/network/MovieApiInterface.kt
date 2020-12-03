package ru.mikhailskiy.intensiv.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.QueryName
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.data.MoviesResponse


interface MovieApiInterface {

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<MoviesResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<MoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String
    ): Single<MoviesResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<MoviesResponse>

    @GET("movie/get-movie-details")
    fun getMovieDetails(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<MoviesResponse>

    @GET("movie/get-movie-credits")
    fun getMovieCredits(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<MoviesResponse>

    @GET("tv/popular")
    fun getPopularTvShows(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<MoviesResponse>

}
