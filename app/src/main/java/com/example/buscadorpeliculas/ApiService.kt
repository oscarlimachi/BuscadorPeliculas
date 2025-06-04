package com.example.buscadorpeliculas

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/")
    suspend fun searchMovies(
        @Query("s") query: String,
        @Query("apikey") apiKey: String = "af342414"
    ): SearchResponse

    @GET("/")
    suspend fun getMovieId(
        @Query("i") imdbID: String,
        @Query("apikey") apiKey: String = "af342414"
    ): Movie




    companion object {
        fun getInstance(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
