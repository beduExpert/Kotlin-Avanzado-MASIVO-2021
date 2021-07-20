package org.bedu.emisor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var emitterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emitterButton = findViewById(R.id.emitterButton)

        val bundle = Bundle().apply{
            putString("NAME", "Juan")
            putBoolean("IS_USER",false)
            putString("APP_VERSION","2.3")
            putInt("CREDIT",22834)
        }

        emitterButton.setOnClickListener{
            Intent("org.bedu.actions.DATA_TRANSFER").apply {
                putExtras(bundle)
            }.let(::sendBroadcast)

        }
    }
}