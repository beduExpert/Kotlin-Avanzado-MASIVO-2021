package org.bedu.notifications.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import org.bedu.notifications.BeduActivity
import org.bedu.notifications.NotificationApp.Companion.CHANNEL_ID
import org.bedu.notifications.NotificationApp.Companion.CHANNEL_OTHERS
import org.bedu.notifications.NotificationApp.Companion.GRUPO_SIMPLE
import org.bedu.notifications.NotificationReceiver
import org.bedu.notifications.R


// el nombre de la acción a ejecutar por el botón en la notificación
const val ACTION_RECEIVED = "action_received"

@SuppressLint("MissingPermission")
fun simpleNotification(context: Context){

    val builder = simpleNotificationBuilder(context, R.string.simple_title, R.string.simple_body)
    val builder2 = simpleNotificationBuilder(context, R.string.simple_title, R.string.simple_body)
    val builder3 = simpleNotificationBuilder(context, R.string.simple_title, R.string.simple_body)

    val summaryNotification = NotificationCompat.Builder(context, CHANNEL_OTHERS)
        .setSmallIcon(R.drawable.bedu_icon)
        .setGroup(GRUPO_SIMPLE)
        .setGroupSummary(true)
        .build()

    //lanzamos la notificación
    with(NotificationManagerCompat.from(context)) {
        notify(20, builder.build())
        notify(21, builder2.build())
        notify(22, builder3.build())
        notify(23, summaryNotification)
    }
}

private fun simpleNotificationBuilder(context: Context, titleId: Int, contentId: Int) =
    NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.triforce)
        .setColor(context.getColor(R.color.triforce))
        .setContentTitle(context.getString(titleId))
        .setContentText(context.getString(contentId))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setGroup(GRUPO_SIMPLE)

@SuppressLint("MissingPermission")
fun touchNotification(activity: Context) {
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
        notify(25, builder.build())
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
        notify(30, builder.build())
    }
}

@SuppressLint("MissingPermission")
fun expandableNotification(context: Context) {
    with(context) {
        val notification = NotificationCompat.Builder(this, CHANNEL_OTHERS)
            .setSmallIcon(R.drawable.bedu_icon)
            .setContentTitle(getString(R.string.simple_title))
            .setContentText(getString(R.string.large_text))
            .setLargeIcon(getDrawable(R.drawable.bedu)?.toBitmap()) // ícono grande a la derecha
            .setStyle(NotificationCompat.BigTextStyle() // este estilo define al expandible
                .bigText(getString(R.string.large_text)))
            .build()

        with(NotificationManagerCompat.from(this)) {
            notify(50, notification)
        }
    }
}

@SuppressLint("MissingPermission")
fun customNotification(context: Context) {

    with(context) {
        // obtenemos los layouts por medio de RemoteViews
        val notificationLayout = RemoteViews(packageName, R.layout.notification_custom)
        val notificationLayoutExpanded = RemoteViews(packageName, R.layout.notification_custom_expanded)


        var notification = NotificationCompat.Builder(this, CHANNEL_OTHERS)
            .setSmallIcon(R.drawable.bedu_icon)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle()) //este estilo define que es personalizable
            .setCustomContentView(notificationLayout) //contenido en modo colapsado
            .setCustomBigContentView(notificationLayoutExpanded) //contenido en modo expandido
            .build()

        with(NotificationManagerCompat.from(this)) {
            notify(60, notification)
        }
    }
}

fun cancelNotifications(context: Context){
    with(NotificationManagerCompat.from(context)) {
        cancelAll()
    }
}

