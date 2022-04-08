package io.solidcrafts.playermaker.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface MovieDbApi {
    @GET("/3/movie/popular")
    suspend fun getPopularMoviesAsync(): Deferred<NetworkMoviesResponse>

    @GET("/3/movie/upcoming")
    suspend fun getUpcomingMoviesAsync(): Deferred<NetworkMoviesResponse>

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMoviesAsync(): Deferred<NetworkMoviesResponse>
}
