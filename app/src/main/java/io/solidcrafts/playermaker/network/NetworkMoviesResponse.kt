package io.solidcrafts.playermaker.network

import com.squareup.moshi.JsonClass
import io.solidcrafts.playermaker.database.DatabaseMovie

@JsonClass(generateAdapter = true)
data class NetworkMoviesResponse(val results: List<NetworkMovie>)

@JsonClass(generateAdapter = true)
data class NetworkMovie(
    val id: Int,
    val title: String
)

fun NetworkMoviesResponse.asDatabaseModel(): Array<DatabaseMovie> {
    return results.map {
        DatabaseMovie(
            id = it.id,
            name = it.title
        )
    }.toTypedArray()
}