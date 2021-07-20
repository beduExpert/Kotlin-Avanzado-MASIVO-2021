package org.bedu.solucion

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast

class PhoneCallReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val pendingResult = goAsync()
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        when(telephonyManager.callState){
            TelephonyManager.CALL_STATE_RINGING ->
                Toast.makeText(
                        context," Llamada entrante",
                        Toast.LENGTH_SHORT)
                    .show()
            TelephonyManager.CALL_STATE_IDLE ->{
                Toast.makeText(
                    context," Grabación terminada",
                    Toast.LENGTH_SHORT)
                    .show()

                context.unregisterReceiver(this)
                pendingResult.finish()

            }

            TelephonyManager.CALL_STATE_OFFHOOK ->{

            }
        }

    }

}
