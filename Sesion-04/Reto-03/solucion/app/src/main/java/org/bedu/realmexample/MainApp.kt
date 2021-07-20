package org.bedu.realmexample

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import org.json.JSONArray

class MainApp: Application() {

    override fun onCreate() {
        super.onCreate()

        //inicializamos Realm
        Realm.init(this)

        //guardar nuestro json en un JSON array
        val array = JSONArray(getJsonFile())

        //configuraciónn de nuestra base de datos
        val config = RealmConfiguration
            .Builder()
                //Aquí inicializamos los datos iterando cada objeto JSON
            .initialData { realm ->
                for ( i in 0 until array.length() ) {
                    //Seteando nuestros valores en Realm
                    val c = realm.createObject(Contact::class.java, i)
                    c.name = array.getJSONObject(i).getString("name")
                    c.job = array.getJSONObject(i).getString("title")
                    c.city = array.getJSONObject(i).getString("city")
                    c.company = array.getJSONObject(i).getString("company")
                }
            }
            .deleteRealmIfMigrationNeeded()
            .name("realmDB.realm") //seteando el nombre de la DB
            .build()

        //Seteamos los datos de configuración en nuestra clase
        Realm.setDefaultConfiguration(config)
    }

    fun getJsonFile(): String{

        return applicationContext
            .assets
            .open("data.json").bufferedReader().use {it.readText()}
    }

}