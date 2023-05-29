package org.bedu.okhttpygson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import org.bedu.okhttpygson.databinding.ActivityMainBinding
import org.json.JSONException
import java.io.IOException

class MainActivity : AppCompatActivity(){

    private val url = "https://swapi.dev/api/people/1/"
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnJedi.setOnClickListener{
            llamarALaFuerza()
        }

        binding.btnSith.setOnClickListener{
            llamarALaFuerza(true)
        }
    }

    fun llamarALaFuerza(isSith: Boolean = false){

        //instanciando al cliente
        val okHttpClient = OkHttpClient()

        //El objeto Request contiene todos los parámetros de la petición (headers, url, body etc)
        val request = Request.Builder()
            .url(url)
            .build()

        val clientBuilder = okHttpClient.newBuilder()

        //si es sith, permitiremos que el interceptor modifique la url
        if(isSith){
            clientBuilder.addInterceptor(InterceptCharacter())
        }

            clientBuilder.build()
            .newCall(request)
            .enqueue(object : Callback {

            //el callback a ejecutar cuando hubo un error
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Error",e.toString())
            }

            //el callback a ejectutar cuando obtuvimos una respuesta
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                body?.let { Log.d("Response: ", it) }

                try {

                    val jedi = Gson().fromJson(body,Jedi::class.java)

                    println(jedi.toString())

                    runOnUiThread {
                        binding. tvName.text = jedi.name
                        binding.tvHeight.text = jedi.height.toString()
                        binding.tvWeight.text = jedi.mass.toString()
                    }

                   /*val json = JSONObject(body)
                    val name = json.getString("name")
                    val height = json.getString("height")
                    val mass = json.getString("mass")

                    runOnUiThread{
                        tvName.text = name
                        tvHeight.text = height
                        tvWeight.text = mass
                    }*/

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }

}
