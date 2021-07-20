package org.bedu.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.SharedPreferences



class MainActivity : AppCompatActivity() {

    companion object{
        val PREFS_NAME = "org.bedu.login"
        val EMAIL = "email"
        val IS_LOGGED = "is_logged"
    }

    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) //Modo privado

        btnLogin.setOnClickListener {
            saveData()
            goToLogged()
        }
    }

    override fun onStart() {
        super.onStart()

        if(isLogged()){
            goToLogged()
        }

    }

    fun saveData(){
        val email = etMail.text.toString()

        preferences.edit()
            .putString(EMAIL, email)
            .putBoolean(IS_LOGGED,true)
            .apply() //a diferencia de commit, apply hace los cambios en el momento, pausando el Thread
    }

    fun goToLogged(){
        //cambiar de actividad sin poder regresar a esta con back button
        val i = Intent(this, LoggedActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }

    fun isLogged(): Boolean{
        return preferences.getBoolean(IS_LOGGED, false)
    }
}
