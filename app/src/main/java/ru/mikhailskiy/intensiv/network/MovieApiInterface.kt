package ru.mikhailskiy.intensiv.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mikhailskiy.intensiv.data.MoviesResponse


interface MovieApiInterface{

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key")apiKey: String, @Query("language")language: String):Call<MoviesResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("api_key")apiKey:String, @Query("language")language:String): Call<MoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("api_key")apiKey:String, @Query("language")language:String): Call<MoviesResponse>

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key")apiKey:String, @Query("language")language:String): Call<MoviesResponse>

    @GET("movie/get-movie-details")
    fun getMovieDetails(@Query("api_key")apiKey:String, @Query("language")language:String): Call<MoviesResponse>

    @GET("movie/get-movie-credits")
    fun getMovieCredits(@Query("api_key")apiKey:String, @Query("language")language:String): Call<MoviesResponse>

    @GET("tv/get-popular-tv-shows")
    fun getPopularTvShows(@Query("api_key")apiKey:String, @Query("language")language:String): Call<MoviesResponse>

}
