package org.bedu.emisor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.bedu.emisor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bundle = Bundle().apply{
            putString("NAME", "Juan")
            putBoolean("IS_USER",false)
            putString("APP_VERSION","2.3")
            putInt("CREDIT",22834)
        }

        binding.emitterButton.setOnClickListener{

            CoroutineScope(Dispatchers.Main).launch {
                delay(1000)
                Intent("org.bedu.actions.DATA_TRANSFER").apply {
                    putExtras(bundle)
                }.let(::sendBroadcast)
            }

        }
    }
}