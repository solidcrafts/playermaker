package io.solidcrafts.playermaker.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.solidcrafts.playermaker.database.DatabaseMovie
import io.solidcrafts.playermaker.domain.MovieTag
import io.solidcrafts.playermaker.domain.MovieTag.*

@JsonClass(generateAdapter = true)
data class NetworkMoviesResponse(
    val results: List<NetworkMovie>,
    @Transient var tag: MovieTag? = null
)

@JsonClass(generateAdapter = true)
data class NetworkMovie(
    val id: Int,
    val title: String,
    @Json(name = "poster_path") val posterPath: String,
)

fun NetworkMoviesResponse.tag(tag: MovieTag): NetworkMoviesResponse = apply { this.tag = tag }

fun List<NetworkMoviesResponse>.asDatabaseModel(): Array<DatabaseMovie> {
    val list = arrayListOf<DatabaseMovie>()

    forEach {
        list.addAll(it.results.map { movie ->
            DatabaseMovie(
                id = movie.id,
                title = movie.title,
                posterPath = movie.posterPath,
                isPopular = it.tag == POPULAR,
                isUpcoming = it.tag == UPCOMING,
                isTopRated = it.tag == TOP_RATED
            )
        })
    }

    return list.toTypedArray();
}