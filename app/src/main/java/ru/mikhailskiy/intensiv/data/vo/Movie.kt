package ru.mikhailskiy.intensiv.data.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.mikhailskiy.intensiv.BuildConfig

@Parcelize
class Movie(
    val posterPath: String?,
    var adult: Boolean?,
    var overview: String?,
    var releaseDate: String?,
    var genreIds: Array<Int>?,
    var id: Long,
    var originalTitle: String?,
    var originalLanguage: String?,
    var title: String?,
    var backDropPath: String?,
    var popularity: Number?,
    var voteCount: Int?,
    var video: Boolean?,
    var voteAverage: Double,
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