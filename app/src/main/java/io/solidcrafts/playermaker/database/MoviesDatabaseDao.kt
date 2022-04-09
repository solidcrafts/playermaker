package io.solidcrafts.playermaker.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MoviesDatabaseDao {
    @Query("select * from databasemovie")
    fun getAllMovies(): LiveData<List<DatabaseMovie>>

    @Query("select * from databasemovie where isPopular")
    fun getPopularMovies(): LiveData<List<DatabaseMovie>>

    @Query("select * from databasemovie where isUpcoming")
    fun getUpcomingMovies(): LiveData<List<DatabaseMovie>>

    @Query("select * from databasemovie where isTopRated")
    fun getTopRateMovies(): LiveData<List<DatabaseMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseMovie)
}