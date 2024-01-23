package com.example.retrofittutorial.model

import com.example.retrofittutorial.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCallService {
    private val BASE_URL = "https://swapi.dev/api/"

    val client = OkHttpClient.Builder()

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        if(BuildConfig.DEBUG){
            client.addInterceptor(logging)
        }

    }

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client.build())
        .build()
        .create(ApiCall::class.java)

    fun call() = api.callGet()
}