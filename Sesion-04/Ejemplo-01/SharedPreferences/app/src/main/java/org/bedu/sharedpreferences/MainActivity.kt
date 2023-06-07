package org.bedu.sharedpreferences

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.bedu.sharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private val PREFS_NAME = "org.bedu.sharedpreferences"
    private val STRING_KEY = "string_key"
    private val NUMBER_KEY = "number_key"
    private val BOOLEAN_KEY = "boolean_key"

    private lateinit var preferences: SharedPreferences
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) //Modo privado

        setValues()

        with (binding) {
            button.apply{
                setOnClickListener {
                    //obtenemos los valores de las vistas
                    val string = etString.text.toString()
                    val number = etNumber.text.toString().toFloat()
                    val checked = switch1.isChecked

                    //las asignamos a nuestra colección y aplicamos
                    preferences.edit()
                        .putString(STRING_KEY, string)
                        .putBoolean(BOOLEAN_KEY,checked)
                        .putFloat(NUMBER_KEY,number)
                        .apply()

                }

                setOnLongClickListener {
                    val intent = Intent(this@MainActivity, DataStoreActivity::class.java)
                    this@MainActivity.startActivity(intent)
                    true
                }
            }
        }

    }

    fun setValues(){
        val string = preferences.getString(STRING_KEY,"")
        val boolean = preferences.getBoolean(BOOLEAN_KEY, false)
        val number = preferences.getFloat(NUMBER_KEY,0f)

        with (binding) {
            //los atamos a sus vistas
            etString.setText(string)
            switch1.isChecked = boolean
            etNumber.setText(number.toString())
        }
    }



}
