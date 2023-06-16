package org.bedu.notifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.bedu.notifications.databinding.ActivityMainBinding
import org.bedu.notifications.utils.buttonNotification
import org.bedu.notifications.utils.executeOrRequestPermission
import org.bedu.notifications.utils.simpleNotification
import org.bedu.notifications.utils.touchNotification


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        with (binding) {
            btnNotify.setOnClickListener{
                executeOrRequestPermission(this@MainActivity) {
                    simpleNotification(this@MainActivity)
                }
            }

            btnActionNotify.setOnClickListener {
                executeOrRequestPermission(this@MainActivity) {
                    touchNotification(this@MainActivity)
                }
            }

            btnNotifyWithBtn.setOnClickListener {
                executeOrRequestPermission(this@MainActivity) {
                    buttonNotification(this@MainActivity)
                }
            }
        }
    }





}
