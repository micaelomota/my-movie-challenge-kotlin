package com.example.user.moviechallengekotlin.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "favoriteMovie")
data class FavoriteMovie(@PrimaryKey var id: Int,
                         @ColumnInfo(name = "title") var title: String,
                         @ColumnInfo(name = "overview") var overview: String,
                         @ColumnInfo(name = "posterPath") var posterPath: String) {

    constructor():this(0, "", "", "")
}