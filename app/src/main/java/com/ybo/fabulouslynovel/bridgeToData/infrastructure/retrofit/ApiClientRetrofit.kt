package com.ybo.fabulouslynovel.bridgeToData.infrastructure.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClientRetrofit {
    private const val BASE_URL: String = "https://api.seatgeek.com/2/"//"https://avezvouslesharingan.fr/"//
    const val CLIENT_ID : String = "MzM4MTc0ODF8MTY4NDU3Mjc0My4wNTY0OTMz"

    private val gson : Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    private val httpClient : OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiService : RetrofitBasedElementaryApiCalls.Retrofit by lazy{
        retrofit.create(RetrofitBasedElementaryApiCalls.Retrofit::class.java)
    }
}