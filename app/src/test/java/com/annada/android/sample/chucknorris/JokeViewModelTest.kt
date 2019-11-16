package com.annada.android.sample.chucknorris

import androidx.lifecycle.Observer
import com.annada.android.sample.chucknorris.model.entities.Joke
import com.annada.android.sample.chucknorris.ui.main.JokeViewModel
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.mockito.Mockito

@RunWith(JUnit4::class)
class JokeViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sut: JokeViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {
        sut = JokeViewModel()
    }

    @Test
    fun `test members updated when model changes`() {
        val joke = Joke(1, "dummy_joke", null)
        sut.bind(joke)

        Assert.assertEquals(
            "joke should be dummy_joke",
            "dummy_joke",
            sut.getJoke().value
        )

        Assert.assertEquals(
            "joke id should be 1",
            "1",
            sut.getJokeId().value
        )
    }

    @Test
    fun `test livedata notifies when it is observed`() {
        val observer = mock<Observer<String>>()
        sut.getJoke().observeForever(observer)
        sut.getJokeId().observeForever(observer)

        val argumentCaptor = ArgumentCaptor.forClass(String::class.java)

        val joke = Joke(1, "dummy_joke1", null)
        sut.bind(joke)

        Assert.assertEquals(
            "joke should be dummy_joke1",
            "dummy_joke1",
            sut.getJoke().value
        )

        Assert.assertEquals(
            "joke id should be 1",
            "1",
            sut.getJokeId().value
        )

        //Check LiveData observer
        argumentCaptor.run {
            Mockito.verify(observer, Mockito.times(2)).onChanged(capture())
        }
    }

}