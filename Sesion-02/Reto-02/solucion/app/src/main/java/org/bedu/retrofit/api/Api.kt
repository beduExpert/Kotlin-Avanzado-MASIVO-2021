package org.bedu.retrofit.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.bedu.retrofit.retrofit.WebServices
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Api {
    private val TIMEOUT_CALL_SECONDS = 30L

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(TIMEOUT_CALL_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_CALL_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_CALL_SECONDS, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val endpoint = retrofit.create(WebServices::class.java)
}