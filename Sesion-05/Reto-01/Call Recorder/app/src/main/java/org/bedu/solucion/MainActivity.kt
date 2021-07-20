package org.bedu.solucion

import android.Manifest.permission.READ_PHONE_STATE
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private lateinit var recordButton: Button
    private val pcReceiver = PhoneCallReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recordButton = findViewById(R.id.recordButton)

        recordButton.setOnClickListener {


            if(!hasPermission()){
                askPermission()
            } else{
                recordCallIfExists()
            }
        }
    }

    private fun hasPermission(): Boolean{
        return ContextCompat.checkSelfPermission(
                this,
                READ_PHONE_STATE ) == PackageManager.PERMISSION_GRANTED

    }

    private fun askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(READ_PHONE_STATE), 1)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {

            // Checking whether user granted the permission or not.
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                recordCallIfExists()
            } else {
                Toast.makeText(
                    this,
                    "No puedes acceder sin este permiso",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

    }


    private fun recordCallIfExists(){
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        if (telephonyManager.callState == TelephonyManager.CALL_STATE_OFFHOOK) {
            Toast.makeText(this, "Grabando llamada", Toast.LENGTH_SHORT).show()
            registerReceiver(
                pcReceiver,
                IntentFilter("android.intent.action.PHONE_STATE")
            )

        } else {
            Toast.makeText(this, "No estás en ninguna llamada", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroy() {

        unregisterReceiver(pcReceiver)
        super.onDestroy()
    }


}