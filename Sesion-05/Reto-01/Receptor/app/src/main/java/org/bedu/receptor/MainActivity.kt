package org.bedu.receptor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

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

            Toast.makeText(context,"""
                Name: $name
                Is user: $isUser
                app version: $version
                current credit: $credit
            """.trimIndent(),
            Toast.LENGTH_SHORT).show()

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(dataReceiver)
    }
}