package org.bedu.firebasenotifications

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

class FirebaseApp: Application() {

    companion object {
        const val CHANNEL_ID = "DEFAULT_CHANNEL"
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNotificationChannel(){
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Canal Generico",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "CANAL GENERICO"
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

}