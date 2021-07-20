package org.bedu.okhttpexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import kotlin.random.Random

class MainActivity : AppCompatActivity(){

    private val baseUrl = "https://swapi.dev/api/planets/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRequest.setOnClickListener{
           llamadaAsincrona()
        }

        btnSincrono.setOnClickListener{
            Thread{
                llamadaSincrona()
            }.start()
        }
    }

    //ponemos en cola la petición en un hilo, y se llama a un callback en otro hilo cuando la respuesta está lista
    fun llamadaAsincrona(){

        //instanciando al cliente
        val okHttpClient = OkHttpClient()

        //obteniendo la url completa
        val planetNumber = Random.nextInt(1,60) //son 60 planetas
        val url = "$baseUrl$planetNumber/"
        Log.d("Response", url)

        //El objeto Request contiene todos los parámetros de la petición (headers, url, body etc)
        val request = Request.Builder()
            .url(url)
            .build()

        //enviando y recibiendo las llamadas de forma asíncrona
        okHttpClient.newCall(request).enqueue(object : Callback {

            //el callback a ejecutar cuando hubo un error
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Response", e.toString())
                Log.e("Error",e.toString())
            }

            //el callback a ejectutar cuando obtuvimos una respuesta
            override fun onResponse(call: Call, response: Response) {
                Log.d("Response", response.toString())
                val body = response.body?.string()
                Log.d("Response", body)

                try {
                    val json = JSONObject(body)

                    val phrase = getString(R.string.choosen_planet)
                    val planet = json.getString("name")

                    runOnUiThread{
                        textView.text ="$phrase $planet"
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }


    //Nuestro thread se bloquea hasta recuperar la información
    fun llamadaSincrona(){

        val client = OkHttpClient()

        //obteniendo la url completa
        val planetNumber = Random.nextInt(1,61) //son 61 planetas
        val url = "$baseUrl$planetNumber/"

        val request =  Request.Builder()
                .url(url)
                .build()

        try {
            val response = client.newCall(request).execute()
            val body = response.body?.string()
            Log.d("Response: ", body)

            val json = JSONObject(body)
            val phrase = getString(R.string.choosen_planet)
            val planet = json.getString("name")
            runOnUiThread{
                textView.text ="$phrase $planet"
            }
        } catch (e: Error){
            Log.e("Error",e.toString())
        }
    }


}
