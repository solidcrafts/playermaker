package io.solidcrafts.playermaker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.solidcrafts.playermaker.database.DatabaseMovie
import io.solidcrafts.playermaker.database.MoviesDatabase
import io.solidcrafts.playermaker.database.asDomainModel
import io.solidcrafts.playermaker.domain.DomainMovie
import io.solidcrafts.playermaker.domain.MovieTag
import io.solidcrafts.playermaker.domain.MovieTag.*
import io.solidcrafts.playermaker.network.NetworkService
import io.solidcrafts.playermaker.network.asDatabaseModel
import io.solidcrafts.playermaker.network.tag
import io.solidcrafts.playermaker.util.LoadingStatus
import io.solidcrafts.playermaker.util.LoadingStatus.DONE
import io.solidcrafts.playermaker.util.LoadingStatus.LOADING
import kotlinx.coroutines.*

class MoviesRepository(
    private val moviesDatabase: MoviesDatabase,
    private val networkService: NetworkService
) {
    private val repositoryScope = CoroutineScope(Dispatchers.IO)
    private val loadingStatus = MutableLiveData<LoadingStatus>()

    private val allMovies = transformToDomainModels(moviesDatabase.moviesDao.getAllMovies())
    private val popularMovies = transformToDomainModels(moviesDatabase.moviesDao.getPopularMovies())
    private val upcomingMovies = transformToDomainModels(moviesDatabase.moviesDao.getUpcomingMovies())
    private val topRatedMovies = transformToDomainModels(moviesDatabase.moviesDao.getTopRateMovies())

    private fun transformToDomainModels(
        data: LiveData<List<DatabaseMovie>>
    ): LiveData<List<DomainMovie>> {
        return Transformations.map(data) {
            it.asDomainModel()
        }
    }

    fun movies(tag: MovieTag? = null): LiveData<List<DomainMovie>> = when (tag) {
        POPULAR -> popularMovies
        UPCOMING -> upcomingMovies
        TOP_RATED -> topRatedMovies
        else -> allMovies
    }

    fun refreshMovies() {
        repositoryScope.launch {
            loadingStatus.postValue(LOADING)
            val upcoming =
                async { networkService.movieDbApi.getUpcomingMoviesAsync().tag(UPCOMING) }
            val popular =
                async { networkService.movieDbApi.getPopularMoviesAsync().tag(POPULAR) }
            val topRated =
                async { networkService.movieDbApi.getTopRatedMoviesAsync().tag(TOP_RATED) }

            val results = awaitAll(upcoming, popular, topRated)
            moviesDatabase.moviesDao.insertAll(*results.asDatabaseModel())
            loadingStatus.postValue(DONE)
        }
    }

    fun status(): LiveData<LoadingStatus> = loadingStatus
}