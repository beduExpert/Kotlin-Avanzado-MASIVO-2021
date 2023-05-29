package org.bedu.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.squareup.picasso.Picasso
import org.bedu.retrofit.api.Api
import org.bedu.retrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener{
            val pokeSearch = binding.etPokemon.text.toString()
            val call = Api.endpoint.getPokemon(pokeSearch)

            call.enqueue(object : Callback<Pokemon> {
                override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Hubo un error", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    if(response.code()==200){
                        val body = response.body()
                        Log.e("Respuesta","${response.body().toString()}")

                        binding.tvPokemon.text = body?.name
                        binding.tvWeight.text = "peso: " + body?.weight.toString()
                        Picasso.get().load(body?.sprites?.photoUrl).into(binding.pokemon);
                    } else{
                        Log.e("Not200","Error not 200: $response")
                        Toast.makeText(this@MainActivity, "No se encontró el pokemon", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }

    }
}
