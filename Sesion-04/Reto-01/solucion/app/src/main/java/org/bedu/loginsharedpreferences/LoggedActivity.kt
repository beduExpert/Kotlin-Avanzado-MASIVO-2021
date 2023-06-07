package org.bedu.loginsharedpreferences

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.bedu.loginsharedpreferences.MainActivity
import org.bedu.loginsharedpreferences.databinding.ActivityLoggedBinding

class LoggedActivity: AppCompatActivity(){

    lateinit var preferences: SharedPreferences
    private lateinit var binding: ActivityLoggedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoggedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE) //Modo privado

        setValues()

        binding.btnClose.setOnClickListener {
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

        binding.tvEmail.text = email
    }

    fun resetShared(){
        preferences.edit().clear().commit() //a diferencia de apply, este cambio se hace de forma asíncrona
    }
}