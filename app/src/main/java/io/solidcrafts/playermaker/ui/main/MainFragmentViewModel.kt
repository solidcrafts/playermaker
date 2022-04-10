package io.solidcrafts.playermaker.ui.main

import android.app.Application
import androidx.lifecycle.*
import io.solidcrafts.playermaker.database.MoviesDatabase
import io.solidcrafts.playermaker.domain.MoviesRow
import io.solidcrafts.playermaker.domain.Movie
import io.solidcrafts.playermaker.domain.MovieTag.*
import io.solidcrafts.playermaker.network.NetworkService
import io.solidcrafts.playermaker.repository.MoviesRepository
import io.solidcrafts.playermaker.util.LoadingStatus
import io.solidcrafts.playermaker.util.ScrollStateHolder
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val moviesRepository =
        MoviesRepository(MoviesDatabase.getInstance(application), NetworkService)
    private val _navigateToSelectedMovie = MutableLiveData<Movie?>()

    val navigateToSelectedMovie: LiveData<Movie?> = _navigateToSelectedMovie
    val status: LiveData<LoadingStatus> = moviesRepository.status()
    var scrollStateHolder: ScrollStateHolder? = null

    fun observableMovieRows(): LiveData<List<MoviesRow>> =
        MediatorLiveData<List<MoviesRow>>().apply {
            value = listOf(
                MoviesRow(UPCOMING),
                MoviesRow(POPULAR),
                MoviesRow(TOP_RATED)
            )

            addSource(moviesRepository.movies(UPCOMING)) {
                value!!.find { d -> d.tag == UPCOMING }?.data = it
                this.postValue(value)
            }
            addSource(moviesRepository.movies(POPULAR)) {
                value!!.find { d -> d.tag == POPULAR }?.data = it
                this.postValue(value)
            }
            addSource(moviesRepository.movies(TOP_RATED)) {
                value!!.find { d -> d.tag == TOP_RATED }?.data = it
                this.postValue(value)
            }
        }

    fun notifyMovieClicked(it: Movie) {
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