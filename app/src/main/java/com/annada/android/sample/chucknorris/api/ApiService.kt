package com.annada.android.sample.chucknorris.api

import com.annada.android.sample.chucknorris.model.entities.Joke
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
//    @GET("/jokes/random/3")
//    suspend fun getQuotes(): List<Quote>


    @GET("/jokes/random/")
    suspend fun getRandomJoke(): ApiResponse<Joke>

    @GET("/jokes/random/{count}")
    suspend fun getRandomJokes(@Path("count") count: Int): ApiResponse<List<Joke>>

    @GET("/jokes/count")
    suspend fun getJokesCount(): ApiResponse<Int>

    @GET("/jokes/{id}")
    suspend fun getJoke(@Path("id") id: Int): ApiResponse<Joke>
}