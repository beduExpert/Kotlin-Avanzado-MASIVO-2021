package org.bedu.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    val PREFS_NAME = "org.bedu.sharedpreferences"
    val STRING = "STRING"
    val NUMBER = "NUMBER"
    val BOOLEAN = "BOOLEAN"

    lateinit var preferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) //Modo privado

        setValues()


        button.setOnClickListener {

            //obtenemos los valores de las vistas
            val string = etString.text.toString()
            val number = etNumber.text.toString().toFloat()
            val checked = switch1.isChecked

            //las asignamos a nuestra colección y aplicamos
            preferences.edit()
                .putString(STRING, string)
                .putBoolean(BOOLEAN,checked)
                .putFloat(NUMBER,number)
                .apply()

        }
    }

    fun setValues(){
        val string = preferences.getString(STRING,"")
        val boolean = preferences.getBoolean(BOOLEAN, false)
        val number = preferences.getFloat(NUMBER,0f)

        //los atamos a sus vistas
        etString.setText(string)
        switch1.isChecked = boolean
        etNumber.setText(number.toString())
    }



}
