package org.bedu.okhttpexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity(){

    private val url = "https://swapi.dev/api/planets/"
    private lateinit var adapter: PlanetAdapter
    var planetList = ArrayList<Planet>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRequest.setOnClickListener{
           llamadaAsincrona()
        }

        adapter = PlanetAdapter(this,planetList)
        lista.adapter = adapter

    }

    //ponemos en cola la petición en un hilo, y se llama a un callback en otro hilo cuando la respuesta está lista
    fun llamadaAsincrona(){

        //instanciando al cliente
        val okHttpClient = OkHttpClient()

        //El objeto Request contiene todos los parámetros de la petición (headers, url, body etc)
        val request = Request.Builder()
            .url(url)
            .build()

        //enviando y recibiendo las llamadas de forma asíncrona
        okHttpClient.newCall(request).enqueue(object : Callback {

            //el callback a ejecutar cuando hubo un error
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Error",e.toString())
            }

            //el callback a ejectutar cuando obtuvimos una respuesta
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                Log.d("Response", body)
                val planetas = ArrayList<Planet>()

                try {
                    val json = JSONObject(body) //se obtiene el objeto raíz
                    val array = json.getJSONArray("results") //se obtiene el arreglo de planetas
                    val numPlanets = array.length() -1 //el número máximo para ejecutar el ciclo for
                    for (i in 0.. numPlanets){ //ciclo for para todos los planetas
                        val planeta = array.getJSONObject(i)  //así obtenemos el planeta del arreglo
                        val name = planeta.getString("name")
                        val terrain = planeta.getString("terrain")
                        val population = planeta.getString("population")

                        val planet = Planet(name,terrain,population)
                        Log.d("Response",planet.toString())
                        planetas.add(planet)
                    }

                    runOnUiThread{
                        populateAdapter(planetas)
                    }


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun populateAdapter(data: ArrayList<Planet>){
        planetList.clear()
        planetList.addAll(data)
        adapter.notifyDataSetChanged()
    }

}
