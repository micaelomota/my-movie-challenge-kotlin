package com.example.user.moviechallengekotlin.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * from favoriteMovie")
    fun getAll(): List<FavoriteMovie>

    @Insert
    fun insert(favoriteMovie: FavoriteMovie)

    @Query("DELETE from favoriteMovie WHERE id = :movieId")
    fun delete(movieId: Int)

    @Query ("SELECT * FROM favoriteMovie WHERE id = :movieId")
    fun getMovieById(movieId: Int): FavoriteMovie
}