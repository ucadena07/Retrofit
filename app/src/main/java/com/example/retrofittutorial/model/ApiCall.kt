package com.example.retrofittutorial.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiCall {
    @GET("people/1")
    fun callGet(): Call<Person>

    @POST("post")
    fun callPost(): Call<Person>

    @GET("apiCall?=name=Rico&age=4")
    fun callQueryStatic(): Call<Person>

    @GET("apiCall")
    fun callQueryDynamic(@Query("name") name: String,@Query("age") age: Int ): Call<Person>
    @GET("apiCall")
    fun callQueryDynamicOptional(@Query("name") name: String,@Query("age") age: Int? ): Call<Person>

    @GET("apiCall")
    fun callMultipleParameters(@QueryMap params: Map<String,String>): Call<Person>
}