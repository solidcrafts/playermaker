package io.solidcrafts.playermaker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.solidcrafts.playermaker.domain.Movie

@Entity
data class DatabaseMovie constructor(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterPath: String,
    val isPopular: Boolean,
    val isUpcoming: Boolean,
    val isTopRated: Boolean
)

fun List<DatabaseMovie>.asDomainModel(): List<Movie> {
    return map {
        Movie(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath
        )
    }
}