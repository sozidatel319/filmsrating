package ru.mikhailskiy.intensiv.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MovieEntity (
    @PrimaryKey
    var id: Long?,
    val posterPath: String?,
    var adult: Boolean?,
    var overview: String?,
    var releaseDate: String?,
    var originalTitle: String?,
    var originalLanguage: String?,
    var title: String?,
    var backDropPath: String?,
    var voteCount: Int?,
    var video: Boolean?,
    var voteAverage: Double,
    var liked:Boolean? = false
)