package org.bedu.receptor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private var dataReceiver = DataReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(dataReceiver, IntentFilter("org.bedu.actions.DATA_TRANSFER"))

    }

    inner class DataReceiver: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val bundle = intent?.extras

            val name = bundle?.getString("NAME")
            val isUser = bundle?.getBoolean("IS_USER")
            val version = bundle?.getString("APP_VERSION")
            val credit = bundle?.getInt("CREDIT")

            Log.d("Receiver",
                """
                Name: $name
                Is user: $isUser
                app version: $version
                current credit: $credit
            """.trimIndent()
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(dataReceiver)
    }
}