package com.annada.android.sample.chucknorris.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.annada.android.sample.chucknorris.model.daos.JokeDao
import com.annada.android.sample.chucknorris.model.entities.Joke

@Database(entities = arrayOf(Joke::class), version = 1)
@TypeConverters(RoomTypeConverters ::class)
abstract class JokeDatabase: RoomDatabase() {

    abstract fun jokeeDao(): JokeDao

    companion object {
        private var INSTANCE: JokeDatabase? = null

        fun getInstance(context: Context): JokeDatabase? {
            if (INSTANCE == null) {
                synchronized(JokeDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        JokeDatabase::class.java, "joke.db"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}