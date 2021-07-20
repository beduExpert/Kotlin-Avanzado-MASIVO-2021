package org.bedu.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.bedu.retrofit.retrofit.WebServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    val TIMEOUT_CALL_SECONDS = 30L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSearch.setOnClickListener{
            println("Esta es una prueba de que va bien todo")

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(TIMEOUT_CALL_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_CALL_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_CALL_SECONDS, TimeUnit.SECONDS)
                .build()


            val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            val endpoint = retrofit.create(WebServices::class.java)

            val pokeSearch = etPokemon.text.toString()

            val call = endpoint.getPokemon(pokeSearch)

            call?.enqueue(object : Callback<Pokemon> {
                override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                    Toast.makeText(applicationContext,"Hubo un error en la comunicación",Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    if(response.code()==200){
                        val body = response.body()
                        Log.e("Respuesta","${response.body().toString()}")

                        tvPokemon.text = body?.name
                        tvWeight.text = "peso: " + body?.weight.toString()
                        Picasso.get().load(body?.sprites?.photoUrl).into(pokemon);
                    } else{
                        Toast.makeText(applicationContext,"Pokemon no encontrado",Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }

    }
}
