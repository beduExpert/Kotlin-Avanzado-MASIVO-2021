package org.bedu.retrofit.api

import org.bedu.retrofit.retrofit.WebServices
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val endpoint = retrofit.create(WebServices::class.java)
}