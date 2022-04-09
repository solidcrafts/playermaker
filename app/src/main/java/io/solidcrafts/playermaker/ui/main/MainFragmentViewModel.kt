package io.solidcrafts.playermaker.ui.main

import android.app.Application
import androidx.lifecycle.*
import io.solidcrafts.playermaker.database.MoviesDatabase
import io.solidcrafts.playermaker.domain.DomainMovie
import io.solidcrafts.playermaker.domain.MovieTag
import io.solidcrafts.playermaker.network.NetworkService
import io.solidcrafts.playermaker.repository.MoviesRepository
import io.solidcrafts.playermaker.util.LoadingStatus
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val moviesRepository =
        MoviesRepository(MoviesDatabase.getInstance(application), NetworkService)

    private val _navigateToSelectedMovie = MutableLiveData<DomainMovie?>()
    val navigateToSelectedMovie: LiveData<DomainMovie?> = _navigateToSelectedMovie

    fun movies(tag: MovieTag) = moviesRepository.movies(tag)
    val status: LiveData<LoadingStatus> = moviesRepository.status()

    fun notifyMovieClicked(it: DomainMovie) {
        _navigateToSelectedMovie.value = it
    }

    fun navigateToSelectedMovieComplete() {
        _navigateToSelectedMovie.value = null
    }

    init {
        viewModelScope.launch {
            moviesRepository.refreshMovies()
        }
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainFragmentViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct MainFragmentViewModel")
        }
    }
}