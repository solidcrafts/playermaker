package io.solidcrafts.playermaker.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.solidcrafts.playermaker.database.DatabaseMovie

@JsonClass(generateAdapter = true)
data class NetworkMoviesResponse(val results: List<NetworkMovie>)

@JsonClass(generateAdapter = true)
data class NetworkMovie(
    val id: Int,
    val title: String,
    @Json(name = "poster_path") val posterUrl: String
)

fun NetworkMoviesResponse.asDatabaseModel(): Array<DatabaseMovie> {
    return results.map {
        DatabaseMovie(
            id = it.id,
            title = it.title,
            posterUrl = it.posterUrl
        )
    }.toTypedArray()
}