package io.solidcrafts.playermaker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.solidcrafts.playermaker.domain.DomainMovie

@Entity
data class DatabaseMovie constructor(
    @PrimaryKey
    val id: Int,
    val name: String
)

fun List<DatabaseMovie>.asDomainModel(): List<DomainMovie> {
    return map {
        DomainMovie(
            id = it.id,
            name = it.name
        )
    }
}