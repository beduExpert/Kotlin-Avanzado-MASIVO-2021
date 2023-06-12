package org.bedu.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class ReceiverOne : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val bundle = intent.extras
        val name = bundle?.getString("NAME")
        val email = bundle?.getString("EMAIL")
        Toast.makeText(context, "$name $email", Toast.LENGTH_SHORT).show()

        Log.d(
            "Broadcast",
            """URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}
                Thread: ${Thread.currentThread()}
            """.trimMargin()
        )


    }
}
