package io.solidcrafts.playermaker.domain

data class CategorizedMovies(val tag: MovieTag, val data: List<DomainMovie>) {
    fun title() = tag.toString()
}