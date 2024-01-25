package com.example.retrofittutorial.model

import android.content.Context
import android.util.Log
import com.example.retrofittutorial.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCallService {
    private val BASE_URL = "https://swapi.dev/api/"


    val client = OkHttpClient.Builder()

//    init {
//        val logging = HttpLoggingInterceptor()
//        logging.level = HttpLoggingInterceptor.Level.BODY
//
//        if(BuildConfig.DEBUG){
//            client.addInterceptor(logging)
//        }
//        client.addInterceptor { chain ->
//            val request = chain.request()
//            val newRequest = request.newBuilder()
//                .addHeader("maz-age", "36000")
//                .build()
//            chain.proceed(newRequest)
//        }
//    }

    private var api2: ApiCall? = null
    fun getApi(context: Context) : ApiCall{
        if(api2 == null){
            val client = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            if(BuildConfig.DEBUG){
                client.addInterceptor(logging)
            }
            client.addInterceptor { chain ->
                val request = chain.request()
                val newRequest = request.newBuilder()
                    .addHeader("user-agent", "android")
                    .build()
                chain.proceed(newRequest)
            }
            api2 = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(ApiCallService.client.build())
                .build()
                .create(ApiCall::class.java)


        }
        return api2!!
    }

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client.build())
        .build()
        .create(ApiCall::class.java)

    fun call(context:Context) = getApi(context).callGet()



    //fun MultipleParamsCall = api.callMultipleParameters(hashMapOf(Pair("a","test"), Pair("b","test b")))
    //fun byPass() = api.callUrlByPass()

    //fun callDynamicUrl() = api.callUrlDynamic("https://example.com/user/info")
    //fun formPostMultiple() = api.callFormDataMultipleFields(hashMapOf(Pair("name","nigel")))
    //fun headerDyn() = api.callHeadersDynamic("rico")
}