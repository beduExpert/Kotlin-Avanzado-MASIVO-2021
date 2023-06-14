package org.bedu.notifications.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.bedu.notifications.BeduActivity
import org.bedu.notifications.NotificationApp.Companion.CHANNEL_ID
import org.bedu.notifications.NotificationReceiver
import org.bedu.notifications.R


// el nombre de la acción a ejecutar por el botón en la notificación
const val ACTION_RECEIVED = "action_received"

@SuppressLint("MissingPermission")
fun simpleNotification(context: Context){
    with(context) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.triforce)
            .setColor(getColor(R.color.triforce))
            .setContentTitle(getString(R.string.simple_title))
            .setContentText(getString(R.string.simple_body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        //lanzamos la notificación
        NotificationManagerCompat
            .from(this)
            .notify(20, builder.build()) // en este caso pusimos un id genérico
    }
}

@SuppressLint("MissingPermission")
fun touchNotification(activity: Activity) {
    // Un PendingIntent para dirigirnos a una actividad pulsando la notificación
    val intent = Intent(activity, BeduActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent: PendingIntent = PendingIntent.getActivity(
        activity,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(activity, CHANNEL_ID)
        .setSmallIcon(R.drawable.bedu_icon)
        .setContentTitle(activity.getString(R.string.action_title))
        .setContentText(activity.getString(R.string.action_body))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent) // se define aquí el content intend
        .setAutoCancel(true) // la notificación desaparece al dar click sobre ella

    with(NotificationManagerCompat.from(activity)) {
        notify(20, builder.build())
    }
}
@SuppressLint("MissingPermission")
fun buttonNotification(activity: Activity) {

    //Similar al anterior, definimos un intent comunicándose con NotificationReceiver
    val acceptIntent = Intent(activity, NotificationReceiver::class.java).apply {
        action = ACTION_RECEIVED
    }
    //creamos un PendingIntent que describe el pending anterior
    val acceptPendingIntent: PendingIntent =
        PendingIntent.getBroadcast(activity, 0, acceptIntent, PendingIntent.FLAG_IMMUTABLE)

    val builder = NotificationCompat.Builder(activity, CHANNEL_ID)
        .setSmallIcon(R.drawable.bedu_icon)
        .setContentTitle(activity.getString(R.string.button_title))
        .setContentText(activity.getString(R.string.button_body))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .addAction(R.drawable.bedu_icon, activity.getString(R.string.button_text), // agregamos la acción
            acceptPendingIntent)

    with(NotificationManagerCompat.from(activity)) {
        notify(20, builder.build())
    }
}
