package io.solidcrafts.playermaker.ui.main

import android.app.Application
import androidx.lifecycle.*
import io.solidcrafts.playermaker.database.MoviesDatabase
import io.solidcrafts.playermaker.network.NetworkService
import io.solidcrafts.playermaker.repository.MoviesRepository
import io.solidcrafts.playermaker.util.LoadingStatus
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus> = _loadingStatus

    private val database = MoviesDatabase.getInstance(application)
    private val moviesRepository = MoviesRepository(database, NetworkService)

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