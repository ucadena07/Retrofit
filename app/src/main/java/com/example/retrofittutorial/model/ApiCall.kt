package com.example.retrofittutorial.model

import retrofit2.Call
import retrofit2.http.GET

interface ApiCall {
    @GET("people/1")
    fun callGet(): Call<Person>

}