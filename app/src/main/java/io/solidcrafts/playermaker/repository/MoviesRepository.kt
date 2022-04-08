package io.solidcrafts.playermaker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import io.solidcrafts.playermaker.database.MoviesDatabase
import io.solidcrafts.playermaker.database.asDomainModel
import io.solidcrafts.playermaker.domain.DomainMovie
import io.solidcrafts.playermaker.network.NetworkService
import io.solidcrafts.playermaker.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(
    private val moviesDatabase: MoviesDatabase,
    private val networkService: NetworkService
) {

    val movies: LiveData<List<DomainMovie>> =
        Transformations.map(moviesDatabase.moviesDatabaseDao.getMovies()) {
            it.asDomainModel()
        }

    suspend fun refreshMovies() {
        withContext(Dispatchers.IO) {
            val response = networkService.movieDbApi.getUpcomingMoviesAsync().await()
            moviesDatabase.moviesDatabaseDao.insertAll(*response.asDatabaseModel())
        }
    }
}