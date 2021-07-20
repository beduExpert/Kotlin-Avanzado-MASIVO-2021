package org.bedu.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AirplaneReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val airplaneState = intent?.let {
            if(it.getBooleanExtra("state",false)) "Prendido" else "Apagado"
        }

        Toast.makeText(context,
            "Modo avión $airplaneState",
            Toast.LENGTH_SHORT)
            .show()
    }
}