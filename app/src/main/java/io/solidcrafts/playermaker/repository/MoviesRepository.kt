package io.solidcrafts.playermaker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import io.solidcrafts.playermaker.database.MoviesDatabase
import io.solidcrafts.playermaker.database.asDomainModel
import io.solidcrafts.playermaker.domain.DomainMovie
import io.solidcrafts.playermaker.domain.MovieTag.*
import io.solidcrafts.playermaker.network.NetworkService
import io.solidcrafts.playermaker.network.asDatabaseModel
import io.solidcrafts.playermaker.network.tag
import kotlinx.coroutines.*

class MoviesRepository(
    private val moviesDatabase: MoviesDatabase,
    private val networkService: NetworkService
) {
    private val repositoryScope = CoroutineScope(Dispatchers.IO)

    val movies: LiveData<List<DomainMovie>> =
        Transformations.map(moviesDatabase.moviesDatabaseDao.getAllMovies()) {
            it.asDomainModel()
        }

    fun refreshMovies() {
        repositoryScope.launch {
            val upcoming =
                async { networkService.movieDbApi.getUpcomingMoviesAsync().tag(UPCOMING) }
            val popular =
                async { networkService.movieDbApi.getPopularMoviesAsync().tag(POPULAR) }
            val topRated =
                async { networkService.movieDbApi.getTopRatedMoviesAsync().tag(TOP_RATED) }

            val results = awaitAll(upcoming, popular, topRated)
            moviesDatabase.moviesDatabaseDao.insertAll(*results.asDatabaseModel())
        }
    }
}