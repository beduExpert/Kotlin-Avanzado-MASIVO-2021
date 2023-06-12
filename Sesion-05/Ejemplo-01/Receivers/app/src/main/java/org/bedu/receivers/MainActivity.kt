package org.bedu.receivers

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import org.bedu.receivers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding
    private val receiverOne = ReceiverOne()
    private val receiverTwo = ReceiverTwo()
    private val airplaneReceiver = AirplaneReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Registramos el receiver de modo avión
        IntentFilter().apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        }.also { filter -> registerReceiver(airplaneReceiver,filter) }

        binding.button.setOnClickListener {

            val bundle = Bundle().apply {
                putString("NAME","Pedro")
                putString("EMAIL","pedro@bedu.org")
            }

//            intent explícito
//            Intent(this,ReceiverOne::class.java).apply {
//                putExtras(bundle)
//            }.let(::sendBroadcast)


            //intent implícito
            Intent("org.debu.actions.SALUDO").apply {
                putExtras(bundle)
            }.let(::sendBroadcast)
        }
    }

    override fun onStart(){
        super.onStart()

        IntentFilter().apply {
            addAction("org.debu.actions.SALUDO")
        }.also { filter -> registerReceiver(receiverTwo,filter) }

    }


    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiverTwo)

    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(airplaneReceiver)

    }

}