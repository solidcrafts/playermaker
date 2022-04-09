package io.solidcrafts.playermaker.domain

data class MoviesRow(val tag: MovieTag, var data: List<Movie>? = null) {
    fun title() = when(tag) {
        MovieTag.POPULAR -> "Popular movies"
        MovieTag.UPCOMING -> "Upcoming movies"
        MovieTag.TOP_RATED -> "Top rated movies"
    }
}