package com.example.buscadorpeliculas

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Title") val Title: String,
    @SerializedName("Year") val Year: String,
    @SerializedName("Poster") val Poster: String,
    @SerializedName("Plot") val Plot: String,
    @SerializedName("Runtime") val Runtime: String,
    @SerializedName("Director") val Director: String,
    @SerializedName("Genre") val Genre: String,
    @SerializedName("Country") val Country: String,
    @SerializedName("Response") val Response: String
)

data class SearchResponse(
    @SerializedName("Search") val Search: List<Movie> = emptyList(),
    @SerializedName("totalResults") val totalResults: String? = null,
    @SerializedName("Response") val Response: String
)

