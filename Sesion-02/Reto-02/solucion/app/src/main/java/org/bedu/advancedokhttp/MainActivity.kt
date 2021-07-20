package org.bedu.advancedokhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONException
import java.io.IOException
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity(){

    private val url = "https://swapi.dev/api/people/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPeople.setOnClickListener{
            llamarALaFuerza()
        }


    }

    fun llamarALaFuerza(){

        //instanciando al cliente
        val okHttpClient = OkHttpClient()

        //El objeto Request contiene todos los parámetros de la petición (headers, url, body etc)
        val request = Request.Builder()
            .url(url)
            .build()

        val clientBuilder = okHttpClient.newBuilder()

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
                try {
                    val newList = Gson().fromJson(body,JediList::class.java)
                    Log.d("Response",newList.toString())


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }

}
