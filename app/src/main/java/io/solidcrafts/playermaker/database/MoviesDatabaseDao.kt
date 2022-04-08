package io.solidcrafts.playermaker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import io.solidcrafts.playermaker.database.DatabaseMovie

@Dao
interface MoviesDatabaseDao {
    @Query("select * from databasemovie")
    fun getMovies(): LiveData<List<DatabaseMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseMovie)
}