package com.example.user.moviechallengekotlin.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [FavoriteMovie::class], version = 2)
abstract class FavoriteMovieDataBase: RoomDatabase() {

    abstract fun favoriteMovieDao(): FavoriteMovieDao

    companion object {
        private var INSTANCE: FavoriteMovieDataBase? = null

        fun getInstance(context: Context): FavoriteMovieDataBase {
            if (INSTANCE == null) {
                synchronized(FavoriteMovieDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            FavoriteMovieDataBase::class.java, "favoriteMovie.db")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}