package com.annada.android.sample.chucknorris.ui.main

import androidx.lifecycle.MutableLiveData
import com.annada.android.sample.chucknorris.base.BaseViewModel
import com.annada.android.sample.chucknorris.model.entities.Joke

class JokeViewModel : BaseViewModel() {
    private val joke = MutableLiveData<String>()
    private val id = MutableLiveData<String>()

    fun bind(jokeObj: Joke) {
        joke.value = jokeObj.joke
        id.value = jokeObj.id.toString()
    }

    fun getJoke(): MutableLiveData<String> {
        return joke
    }

    fun getJokeId(): MutableLiveData<String> {
        return id
    }
}