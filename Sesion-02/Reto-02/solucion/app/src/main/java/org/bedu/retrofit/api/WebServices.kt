package org.bedu.retrofit.retrofit

import org.bedu.retrofit.Pokemon
import org.bedu.retrofit.Type
import retrofit2.Call
import retrofit2.http.*


interface WebServices {
    // Request method and URL specified in the annotation

    @GET("pokemon/{pokemon}")
    fun getPokemon(@Path("pokemon") pokemon: String): Call<Pokemon>

    @GET("type/{id}")
    fun getType(@Path("id") id: Int): Call<List<Type>>

}
