package io.solidcrafts.playermaker.data

import retrofit2.http.GET

interface IMoviesRepository {

    @GET("3/movie/popular")
    suspend fun popularMovies(): List<Movie>

    @GET("3/movie/upcoming")
    suspend fun latestMovies(): List<Movie>

    @GET("3/movie/top_rated")
    suspend fun topRatedMovies(): List<Movie>
}
