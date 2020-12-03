package ru.mikhailskiy.intensiv.data

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("poster_path")
    var posterPath: String?,
    var adult: Boolean?,
    var overview:String?,
    @SerializedName("release_date")
    var releaseDate:String?,
    @SerializedName("genre_ids")
    var genreIds:Array<Int>?,
    var id:Int?,
    @SerializedName("original_title")
    var originalTitle:String?,
    @SerializedName("original_language")
    var originalLanguage:String?,
    var title:String?,
    @SerializedName("backdrop_path")
    var backDropPath:String?,
    var popularity:Number?,
    @SerializedName("vote_count")
    var voteCount:Int?,
    var video:Boolean?,
    @SerializedName("vote_average")
    var voteAverage:Number

    )