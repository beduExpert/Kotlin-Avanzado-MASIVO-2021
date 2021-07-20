package org.bedu.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.bedu.retrofit.retrofit.WebServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSearch.setOnClickListener{
            println("Esta es una prueba de que va bien todo")

            val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val endpoint = retrofit.create(WebServices::class.java)

            val pokeSearch = etPokemon.text.toString()
            val call = endpoint.getPokemon(pokeSearch)

            call?.enqueue(object : Callback<Pokemon> {
                override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                    Log.e("error","Error: $t")
                }

                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    if(response.code()==200){
                        val body = response.body()
                        Log.e("Respuesta","${response.body().toString()}")

                        tvPokemon.text = body?.name
                        tvWeight.text = "peso: " + body?.weight.toString()
                        Picasso.get().load(body?.sprites?.photoUrl).into(pokemon);
                    } else{
                        Log.e("Not200","Error not 200: $response")
                    }
                }

            })
        }

    }
}
