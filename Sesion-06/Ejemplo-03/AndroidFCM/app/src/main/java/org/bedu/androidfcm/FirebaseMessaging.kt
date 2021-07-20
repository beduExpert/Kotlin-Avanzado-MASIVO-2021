package org.bedu.androidfcm

import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessaging: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            sendNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
        }
    }

    private fun sendNotification(title: String?, body: String?){
        Log.d("FCM_MESSAGE", "Cuerpo de la notificación: $body")

        val notificationBuilder = NotificationCompat.Builder(this,MainActivity.CHANNEL_ID)
            .setSmallIcon(R.drawable.bedu_icon)
            .setContentTitle(title)
            .setContentText(body)


        //lanzamos la notificación
        with(NotificationManagerCompat.from(this)) {
            notify(0, notificationBuilder.build()) //en este caso pusimos un id genérico
        }
    }
}