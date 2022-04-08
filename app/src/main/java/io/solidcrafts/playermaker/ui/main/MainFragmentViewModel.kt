package io.solidcrafts.playermaker.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.solidcrafts.playermaker.network.NetworkService
import io.solidcrafts.playermaker.util.LoadingStatus
import kotlinx.coroutines.launch

class MainFragmentViewModel : ViewModel() {

    init {
        //poke loading...
        viewModelScope.launch {
            val result = NetworkService.movieDbApi.getUpcomingMoviesAsync().await()
            Log.i("Alex", "${result.results.size}")
        }
    }

    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus> = _loadingStatus

}