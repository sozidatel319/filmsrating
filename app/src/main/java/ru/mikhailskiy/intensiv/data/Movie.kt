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

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (posterPath != other.posterPath) return false
        if (adult != other.adult) return false
        if (overview != other.overview) return false
        if (releaseDate != other.releaseDate) return false
        if (genreIds != null) {
            if (other.genreIds == null) return false
            if (genreIds!!.contentEquals(other.genreIds!!)) return false
        } else if (other.genreIds != null) return false
        if (id != other.id) return false
        if (originalTitle != other.originalTitle) return false
        if (originalLanguage != other.originalLanguage) return false
        if (title != other.title) return false
        if (backDropPath != other.backDropPath) return false
        if (popularity != other.popularity) return false
        if (voteCount != other.voteCount) return false
        if (video != other.video) return false
        if (voteAverage != other.voteAverage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = posterPath?.hashCode() ?: 0
        result = 31 * result + (adult?.hashCode() ?: 0)
        result = 31 * result + (overview?.hashCode() ?: 0)
        result = 31 * result + (releaseDate?.hashCode() ?: 0)
        result = 31 * result + (genreIds?.contentHashCode() ?: 0)
        result = 31 * result + (id ?: 0)
        result = 31 * result + (originalTitle?.hashCode() ?: 0)
        result = 31 * result + (originalLanguage?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (backDropPath?.hashCode() ?: 0)
        result = 31 * result + (popularity?.hashCode() ?: 0)
        result = 31 * result + (voteCount ?: 0)
        result = 31 * result + (video?.hashCode() ?: 0)
        result = 31 * result + voteAverage.hashCode()
        return result
    }
}