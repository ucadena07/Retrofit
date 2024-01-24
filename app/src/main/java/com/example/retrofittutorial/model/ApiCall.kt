package com.example.retrofittutorial.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url

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

    //This will error
    @GET("https://example.com/user/info")
    fun callUrlByPass(): Call<Person>

    @GET
    fun callUrlDynamic(@Url url: String) : Call<Person>

    @GET("user/{info}")
    fun callUrlPath(@Path("info") info: String): Call<Person>
}