package ru.mikhailskiy.intensiv.data.dto

import com.google.gson.annotations.SerializedName

data class MoviesResponseDTO(
    var page: Int,
    var results: List<MovieDTO>,
    @SerializedName("total_results")
    var totalResults: Int,
    @SerializedName("total_pages")
    var totalPages: Int
)