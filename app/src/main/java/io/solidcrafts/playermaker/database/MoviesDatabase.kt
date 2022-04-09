package io.solidcrafts.playermaker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseMovie::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val moviesDao: MoviesDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MoviesDatabase::class.java,
                        "movies"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}