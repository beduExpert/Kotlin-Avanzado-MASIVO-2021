package org.bedu.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_logged.*

class LoggedActivity: AppCompatActivity(){

    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged)

        preferences = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE) //Modo privado

        setValues()

        btnClose.setOnClickListener {
            resetShared()

            goToLogged()
        }

    }

    fun goToLogged(){
        //cambiar de actividad sin poder regresar a esta con back button
        val i = Intent(this, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }

    fun setValues(){
        //obtenemos los valores desde preferencias
        val email = preferences.getString(MainActivity.EMAIL,"")

        tvEmail.text = email
    }

    fun resetShared(){
        preferences.edit().clear().commit() //a diferencia de apply, este cambio se hace de forma asíncrona
    }
}