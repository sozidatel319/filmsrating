package ru.mikhailskiy.intensiv.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import ru.mikhailskiy.intensiv.BuildConfig

@Parcelize
data class Movie(
    @SerializedName("poster_path")
    val posterPath: String?,
    var adult: Boolean?,
    var overview: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    @SerializedName("genre_ids")
    var genreIds: Array<Int>?,
    var id: Long?,
    @SerializedName("original_title")
    var originalTitle: String?,
    @SerializedName("original_language")
    var originalLanguage: String?,
    var title: String?,
    @SerializedName("backdrop_path")
    var backDropPath: String?,
    var popularity: Number?,
    @SerializedName("vote_count")
    var voteCount: Int?,
    var video: Boolean?,
    @SerializedName("vote_average")
    var voteAverage: Number,
    var liked: Boolean?
) : Parcelable {
    val fullPath: String
        get() {
            return BuildConfig.DOWNLOAD_PICTURE_URL + posterPath
        }
    val fullBackDropPath: String
        get() {
            return BuildConfig.DOWNLOAD_PICTURE_URL + backDropPath
        }
}