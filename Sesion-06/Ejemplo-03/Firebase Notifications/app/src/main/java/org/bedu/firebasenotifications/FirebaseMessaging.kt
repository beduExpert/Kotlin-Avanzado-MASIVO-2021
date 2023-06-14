package org.bedu.firebasenotifications

import android.annotation.SuppressLint
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.bedu.firebasenotifications.FirebaseApp.Companion.CHANNEL_ID

class FirebaseMessaging: FirebaseMessagingService() {

    var token: String? = null

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            sendNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
        }
    }

    override fun onNewToken(token: String) {
        Log.d("TOKEN", "Refreshed token: $token")

        this.token = token
    }

    @SuppressLint("MissingPermission")
    private fun sendNotification(title: String?, body: String?) {
        Log.d("FCM_MESSAGE", "Cuerpo de la notificación: $body")

        val notificationBuilder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.bedu_icon)
            .setContentTitle(title)
            .setContentText(body)


        //lanzamos la notificación
        with(NotificationManagerCompat.from(this)) {
            notify(0, notificationBuilder.build()) //en este caso pusimos un id genérico
        }
    }
}