package com.annada.android.sample.chucknorris.ui.main

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.annada.android.sample.chucknorris.model.JokeDatabase

class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuoteListViewModel::class.java)) {
            val db = Room.databaseBuilder(
                activity.applicationContext,
                JokeDatabase::class.java,
                "joke"
            ).build()
            @Suppress("UNCHECKED_CAST")
            return QuoteListViewModel(db.jokeeDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}