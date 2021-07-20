package org.bedu.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.exp

class MainActivity : AppCompatActivity() {

    //El id de nuestro canal a registrar
    val CHANNEL_CURSOS = "CURSOS"
    val CHANNEL_OTHERS = "OTROS"

    val GRUPO_SIMPLE = "GRUPO_SIMPLE"


    companion object{
        //el nombre de la acción a ejecutar por el botón en la notificación
        const val ACTION_RECEIVED = "action_received"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Para android Oreo en adelante, s obligatorio registrar el canal de notificación
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationChannel()
            setOthersChannel()
        }

        btnNotify.setOnClickListener{
            simpleNotification()
        }

        btnActionNotify.setOnClickListener {
            touchNotification()
        }

        btnNotifyWithBtn.setOnClickListener {
            buttonNotification()
        }

        btnExpandable.setOnClickListener {
            expandableNotification()
        }

        btnCustom.setOnClickListener {
            customNotification()
        }

        btnCancelNoti.setOnClickListener {
            cancelNotifications()
        }
    }


    private fun simpleNotification(){

        var builder = NotificationCompat.Builder(this, CHANNEL_OTHERS)
            .setSmallIcon(R.drawable.triforce) //seteamos el ícono de la push notification
            .setColor(getColor(R.color.triforce)) //definimos el color del ícono y el título de la notificación
            .setContentTitle(getString(R.string.simple_title)) //seteamos el título de la notificación
            .setContentText(getString(R.string.simple_body)) //seteamos el cuerpo de la notificación
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) //Ponemos una prioridad por defecto
            .setGroup(GRUPO_SIMPLE)

        var builder2 = NotificationCompat.Builder(this, CHANNEL_OTHERS)
            .setSmallIcon(R.drawable.triforce) //seteamos el ícono de la push notification
            .setColor(getColor(R.color.triforce)) //definimos el color del ícono y el título de la notificación
            .setContentTitle(getString(R.string.simple_title_2)) //seteamos el título de la notificación
            .setContentText(getString(R.string.simple_body_2)) //seteamos el cuerpo de la notificación
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) //Ponemos una prioridad por defecto
            .setGroup(GRUPO_SIMPLE)

        var builder3 = NotificationCompat.Builder(this, CHANNEL_OTHERS)
            .setSmallIcon(R.drawable.triforce) //seteamos el ícono de la push notification
            .setColor(getColor(R.color.triforce)) //definimos el color del ícono y el título de la notificación
            .setContentTitle(getString(R.string.simple_title_3)) //seteamos el título de la notificación
            .setContentText(getString(R.string.simple_body_3)) //seteamos el cuerpo de la notificación
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) //Ponemos una prioridad por defecto
            .setGroup(GRUPO_SIMPLE)



        val summaryNotification = NotificationCompat.Builder(this@MainActivity, CHANNEL_OTHERS)
            .setSmallIcon(R.drawable.bedu_icon)
            .setGroup(GRUPO_SIMPLE)
            .setGroupSummary(true)
            .build()


        //lanzamos el grupo de notificaciones
        with(NotificationManagerCompat.from(this)) {
            notify(20, builder.build())
            notify(21, builder2.build())
            notify(22, builder3.build())
            notify(23, summaryNotification)
        }

    }

    private fun touchNotification(){

        //Un PendingIntent para dirigirnos a una actividad pulsando la notificación
        val intent = Intent(this, NewBeduActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val acceptIntent = Intent(this, NotificationReceiver::class.java).apply {
            action = ACTION_RECEIVED
        }
        //creamos un PendingIntent que describe el pending anterior
        val acceptPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 0, acceptIntent, 0)


        val builder = NotificationCompat.Builder(this, CHANNEL_CURSOS)
            .setSmallIcon(R.drawable.bedu_icon)
            .setContentTitle(getString(R.string.action_title))
            .setContentText(getString(R.string.action_body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) //se define aquí el content intend
            .setAutoCancel(true) //la notificación desaparece al dar click sobre ella
            .addAction(R.drawable.bedu_icon, getString(R.string.button_text),//agregamos la acción
                acceptPendingIntent)

        with(NotificationManagerCompat.from(this)) {
            notify(30, builder.build())
        }

    }

    private fun buttonNotification(){

        //Similar al anterior, definimos un intent comunicándose con NotificationReceiver
        val acceptIntent = Intent(this, NotificationReceiver::class.java).apply {
            action = ACTION_RECEIVED
        }
        //creamos un PendingIntent que describe el pending anterior
        val acceptPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 0, acceptIntent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_OTHERS)
            .setSmallIcon(R.drawable.bedu_icon)
            .setContentTitle(getString(R.string.button_title))
            .setContentText(getString(R.string.button_body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(R.drawable.bedu_icon, getString(R.string.button_text),//agregamos la acción
                acceptPendingIntent)

        with(NotificationManagerCompat.from(this)) {
            notify(40, builder.build())
        }
    }

    private fun expandableNotification(){

        var notification = NotificationCompat.Builder(this, CHANNEL_OTHERS)
            .setSmallIcon(R.drawable.bedu_icon)
            .setContentTitle(getString(R.string.simple_title))
            .setContentText(getString(R.string.large_text))
            .setLargeIcon(getDrawable(R.drawable.bedu)?.toBitmap()) //ícono grande a la derecha
            .setStyle(NotificationCompat.BigTextStyle() //este estilo define al expandible
                .bigText(getString(R.string.large_text))) //Cuerpo de la notificación cuando se expande
            .build()

        with(NotificationManagerCompat.from(this)) {
            notify(50, notification)
        }

    }

    private fun customNotification(){

        //obtenemos los layouts por medio de RemoteViews
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

    private fun cancelNotifications(){
        with(NotificationManagerCompat.from(this)) {
            cancelAll()
        }
    }

    private fun setNotificationChannel(){
        val name = getString(R.string.channel_courses)
        val descriptionText = getString(R.string.courses_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_CURSOS, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    //SOLUCIÓN 2
    private fun setOthersChannel(){
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_OTHERS, getString(R.string.channel_others), importance).apply {
            description = getString(R.string.others_description)
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }






}
