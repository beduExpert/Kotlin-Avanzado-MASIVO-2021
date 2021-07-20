package org.bedu.advancedokhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity(){

    private val url = "https://swapi.dev/api/people/1/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnJedi.setOnClickListener{
            llamarALaFuerza()
        }

        btnSith.setOnClickListener{
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
                Log.d("Response: ", body)

                try {

                    val jedi = Gson().fromJson(body,Jedi::class.java)

                    println(jedi.toString())

                    runOnUiThread{
                        tvName.text = jedi.name
                        tvHeight.text = jedi.height.toString()
                        tvWeight.text = jedi.mass.toString()
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
