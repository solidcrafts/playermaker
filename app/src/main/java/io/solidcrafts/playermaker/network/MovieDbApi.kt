package io.solidcrafts.playermaker.network

import retrofit2.http.GET

interface MovieDbApi {
    @GET("/3/movie/popular")
    suspend fun getPopularMoviesAsync(): NetworkMoviesResponse

    @GET("/3/movie/upcoming")
    suspend fun getUpcomingMoviesAsync(): NetworkMoviesResponse

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMoviesAsync(): NetworkMoviesResponse
}
