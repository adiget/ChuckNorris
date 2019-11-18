package com.annada.android.sample.chucknorris.model.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.annada.android.sample.chucknorris.model.entities.Joke

@Dao
interface JokeDao {

    @get:Query("SELECT * from joke")
    val all: List<Joke>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(joke: Joke)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg joke: Joke)

    @Query("DELETE from joke")
    fun deleteAll()
}