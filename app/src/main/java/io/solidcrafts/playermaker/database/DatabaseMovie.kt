package io.solidcrafts.playermaker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.solidcrafts.playermaker.domain.DomainMovie

@Entity
data class DatabaseMovie constructor(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterUrl: String
)

fun List<DatabaseMovie>.asDomainModel(): List<DomainMovie> {
    return map {
        DomainMovie(
            id = it.id,
            title = it.title,
            posterUrl = it.posterUrl
        )
    }
}