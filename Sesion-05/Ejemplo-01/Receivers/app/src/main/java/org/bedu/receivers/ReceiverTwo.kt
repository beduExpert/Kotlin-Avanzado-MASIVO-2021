package org.bedu.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReceiverTwo : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        CoroutineScope(Dispatchers.IO).launch {
            val bundle = intent.extras
            val name = bundle?.getString("NAME")
            val email = bundle?.getString("EMAIL")

            Log.d(
                "Broadcast",
                """NAME: $name
            EMAIL: $email
            Thread: ${Thread.currentThread()}
        """.trimMargin()
            )

            delay(1000)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "$name $email", Toast.LENGTH_SHORT).show()
                Log.d("Broadcast", " Thread en Dispatchers.Main: ${Thread.currentThread()}")
            }
        }


        // Legacy (con un AsyncTask)
        /* val pendingResult: PendingResult = goAsync()
        ToastTask(pendingResult, intent.extras,context).run{
            execute()
        }*/
    }



    private inner class ToastTask(
        private val pendingResult: PendingResult,
        private val bundle: Bundle?,
        private val context: Context,
    ): AsyncTask<String,Int,String>(){

        override fun doInBackground(vararg params: String?): String {

            val name = bundle?.getString("NAME")
            val email = bundle?.getString("EMAIL")

            Log.d("Broadcast",
                """NAME: $name
                EMAIL: $email
                Thread: ${Thread.currentThread()}
            """.trimMargin())

            return "Respuesta"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            val name = bundle?.getString("NAME")
            val email = bundle?.getString("EMAIL")

            Toast.makeText(context,"$name $email",Toast.LENGTH_SHORT).show()

            Log.d("Broadcast",
                " Thread de post execute: ${Thread.currentThread()}")

            pendingResult.finish()
        }

    }

}
