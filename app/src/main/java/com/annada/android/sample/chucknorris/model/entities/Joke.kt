package com.annada.android.sample.chucknorris.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "joke")
data class Joke(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int?,
    @ColumnInfo(name = "joke") var joke: String?,
    @ColumnInfo(name = "categories") var categories: List<String>?
) {
    constructor() : this(null, "", null)
}
