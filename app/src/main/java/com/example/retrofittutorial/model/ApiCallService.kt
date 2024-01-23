package com.example.retrofittutorial.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCallService {
    private val BASE_URL = "https://swapi.dev/api/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiCall::class.java)

    fun call() = api.callGet()
}