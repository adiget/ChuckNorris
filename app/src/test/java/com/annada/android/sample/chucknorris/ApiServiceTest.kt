package com.annada.android.sample.chucknorris

import com.annada.android.sample.chucknorris.api.ApiService
import com.annada.android.sample.chucknorris.model.entities.Joke
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ApiServiceTest {

        lateinit var service: ApiService

        @Before
        fun setUp() {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.icndb.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            service = retrofit.create(ApiService::class.java)
        }

        @Test
        fun should_callServiceWithCoroutine() {
            runBlocking {
                val joke = service.getJoke(14)

                Assert.assertEquals(
                    "joke id should be There are no steroids in baseball. Just players Chuck Norris has breathed on.",
                    "There are no steroids in baseball. Just players Chuck Norris has breathed on.",
                    (joke.value as Joke).joke
                )

            }
        }
    }
