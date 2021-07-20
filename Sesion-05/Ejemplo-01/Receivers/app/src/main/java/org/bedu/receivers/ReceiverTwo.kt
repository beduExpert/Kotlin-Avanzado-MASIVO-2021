package org.bedu.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReceiverTwo : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        //Opción más nueva y óptima
        ToastCoroutine(intent.extras,context).run {
            execute()
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

    private inner class ToastCoroutine(
        private val bundle: Bundle?,
        private val context: Context) : ViewModel() {

        fun execute() = viewModelScope.launch {
            doInBackground()
            onPostExecute()
        }

        private suspend fun doInBackground(): String = withContext(Dispatchers.IO) {


            val name = bundle?.getString("NAME")
            val email = bundle?.getString("EMAIL")

            Log.d("Broadcast",
                """NAME: $name
                EMAIL: $email
                Thread: ${Thread.currentThread()}
            """.trimMargin())
            delay(1000) // simulate async work
            return@withContext "Resultado"
        }

        private fun onPostExecute(){
            val name = bundle?.getString("NAME")
            val email = bundle?.getString("EMAIL")
            Toast.makeText(context,"$name $email",Toast.LENGTH_SHORT).show()

            Log.d("Broadcast", " Thread de post execute: ${Thread.currentThread()}")
        }


    }

}
