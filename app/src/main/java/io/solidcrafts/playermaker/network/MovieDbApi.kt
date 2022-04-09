package io.solidcrafts.playermaker.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface MovieDbApi {
    @GET("/3/movie/popular")
    fun getPopularMoviesAsync(): Deferred<NetworkMoviesResponse>

    @GET("/3/movie/upcoming")
    fun getUpcomingMoviesAsync(): Deferred<NetworkMoviesResponse>

    @GET("/3/movie/top_rated")
    fun getTopRatedMoviesAsync(): Deferred<NetworkMoviesResponse>
}
